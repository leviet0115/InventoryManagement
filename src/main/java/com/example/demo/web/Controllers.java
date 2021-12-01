package com.example.demo.web;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Brand;
import com.example.demo.model.BrandRepository;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryRepository;
import com.example.demo.model.Item;
import com.example.demo.model.ItemRepository;
import com.example.demo.model.ItemWrapper;
import com.example.demo.model.PurchaseRequest;
import com.example.demo.model.PurchaseRequestRepository;
import com.example.demo.model.ReItemWrapper;
import com.example.demo.model.RequestItem;
import com.example.demo.model.RequestItemRepository;
import com.example.demo.model.CCUserRepository;
import com.example.demo.model.CatComUser;

@Controller
public class Controllers {
	@Autowired
	private ItemRepository itemRepo;

	@Autowired
	private BrandRepository brandRepo;

	@Autowired
	private CategoryRepository cateRepo;

	@Autowired
	private PurchaseRequestRepository requestRepo;

	@Autowired
	private RequestItemRepository reitemRepo;

	@Autowired
	private CCUserRepository ccUserRepository;

	//Index
	@GetMapping("/")
	public String getIndex(Model model, Authentication authentication) {
		model.addAttribute("role", authentication.getAuthorities());
		return "index";
	}

	//All item controllers
	@GetMapping("/item/new")
	public String getApp(Model model) {
		model.addAttribute("item", new Item());
		model.addAttribute("items", itemRepo.findAll());
		model.addAttribute("brand", new Brand());
		model.addAttribute("brands", brandRepo.findAll());
		model.addAttribute("category", new Category());
		model.addAttribute("categories", cateRepo.findAll());
		return "new_item";
	}

	@PostMapping("/item/save")
	public String postItem(@RequestParam(value = "file", required = false) MultipartFile multipartFile,@ModelAttribute Item item, Model model, final HttpServletRequest httpRequest) throws IOException {
		if (multipartFile != null) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			item.setImage(fileName);
			itemRepo.save(item);
			String uploadDir = "images/item/" + item.getItemId();
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			itemRepo.save(item);
		}

		String referer = httpRequest.getHeader("Referer");
		
		if (referer.contains("edit")) {
			return "redirect:/item/all";
		} 
		
		return "redirect:/request/new/step2";
	}

	@PostMapping("/item/saves")
	public String postItem(@ModelAttribute ItemWrapper itemWrapper, Model model) {
		for (Item it: itemWrapper.getItems()) {
			itemRepo.save(it);
		}
		return "redirect:/item/new";
	}

	@GetMapping("/item/all")
	public String getAll(Model model) {		
		model.addAttribute("items", itemRepo.findAll());
		return "all_items";
	}

	@RequestMapping(value = "item/delete/{id}", method = RequestMethod.GET )
	public String deleteItem(@PathVariable("id") Long item_id, Model model) {
		itemRepo.deleteById(item_id);
		return "redirect:/item/all";
	}

	@RequestMapping(value = "item/edit/{id}", method = RequestMethod.GET )
	public String editItem(@PathVariable("id") Long item_id, Model model ) {
		model.addAttribute("item", itemRepo.findById(item_id));
		model.addAttribute("brands", brandRepo.findAll());
		model.addAttribute("categories", cateRepo.findAll());
		return "edit_item";
	}

	@GetMapping("request/new/step1")
	public String getStep1(Model model) {
		ItemWrapper wrapper = new ItemWrapper();
		itemRepo.findAll().forEach(wrapper::addItem);
		model.addAttribute("wrapper", wrapper);

		return "mass_edit_stock";
	}


	//All brand controllers
	@PostMapping("/brand/save")
	public String saveBrand(@ModelAttribute Brand brand, Model model) {
		brandRepo.save(brand);
		return "redirect:/item/new";
	}

	@RequestMapping(value = "brand/delete/{id}", method = RequestMethod.GET )
	public String deleteBrand(@PathVariable("id") Long brandId, Model model) {
		brandRepo.deleteById(brandId);
		return "redirect:/item/new";
	}

	//All category controller
	@PostMapping("/category/save")
	public String saveCategory(@ModelAttribute Category category, Model model) {
		cateRepo.save(category);
		return "redirect:/item/new";
	}

	@RequestMapping(value = "category/delete/{id}", method = RequestMethod.GET )
	public String deleteCategory(@PathVariable("id") Long categoryId, Model model) {
		cateRepo.deleteById(categoryId);
		return "redirect:/item/new";
	}

	//All request controller
	@GetMapping("/request/new/step2")
	public String selectItem(Model model) {
		model.addAttribute("items", itemRepo.findAll());
		return "add_items_to_request";
	}

	@PostMapping("/request/new/step3")
	public String addItemToRequest(@RequestParam(required =  false) List<String> selected, Model model) {

		PurchaseRequest request = new PurchaseRequest();
		requestRepo.save(request);

		ReItemWrapper wrapper = new ReItemWrapper();

		if(selected == null) {
			return "redirect:request/new/step3";
		}else {
			for (String i: selected) {
				java.util.Optional<Item> searchItem = itemRepo.findById(Long.parseLong(i));
				if (searchItem.isPresent()) {
					Item item = searchItem.get();
					model.addAttribute("item", item);
					RequestItem reItem = new RequestItem(item, request);
					reitemRepo.save(reItem);
					wrapper.addItem(reItem);
				}
			}
			model.addAttribute("requestId", request.getRequestId());
			model.addAttribute("wrapper", wrapper);
			return "input_request_qty";
		}
	}

	@PostMapping("/request/new/step3/save")
	public String saveRequestQuantity(@ModelAttribute ReItemWrapper wrapper, Model model) {
		List<RequestItem> reItems = wrapper.getReItems();
		for (RequestItem i: reItems) {
			reitemRepo.save(i);	}
		PurchaseRequest request = reItems.get(0).getRequest();
		request.setSentTime(request.printDateTime(LocalDateTime.now()));
		requestRepo.save(request);
		return "redirect:/request/" + request.getRequestId() ;
	}

	@RequestMapping(value = "/request/{id}", method = RequestMethod.GET )
	public String getPurchaseRequest(@PathVariable("id") Long requestId, Model model ) {
		model.addAttribute("request", requestRepo.findById(requestId).get());
		return "purchase_request";
	}

	@PostMapping("/request/approve/{id}")
	public String approveRequest(@RequestParam(required =  false) List<String> approved, Model model, @PathVariable("id") Long requestId) {
		PurchaseRequest request = requestRepo.findById(requestId).get();
		if (approved == null) {
			for (RequestItem item: request.getRequestItems()) {
				item.setApproved(false);
				reitemRepo.save(item);
			} 
		}else{
			for (String i: approved) {
				RequestItem item = reitemRepo.findItemByKey(Long.parseLong(i), requestId).get();
				item.setApproved(true);
				reitemRepo.save(item);
			}
		}
		request.setApprovedTime(request.printDateTime(LocalDateTime.now()));
		requestRepo.save(request);
		return "redirect:/request/" + requestId;
	}

	@GetMapping("request/purchase/{id}")
	public String purchaseRequest(@PathVariable("id") Long requestId) {
		PurchaseRequest request = requestRepo.findById(requestId).get();
		request.setPurchasedTime(request.printDateTime(LocalDateTime.now()));
		requestRepo.save(request);

		for (RequestItem reItem: request.getRequestItems()) {
			Item item = reItem.getItem();
			int purchasedQty = reItem.getRequestQty();
			item.addStock(purchasedQty);
			itemRepo.save(item);
		}
		return "redirect:/request/" + requestId;
	}

	@GetMapping("/request/all")
	public String getAllRequests(Model model) {
		Iterable<PurchaseRequest> requests= requestRepo.findAll();
		for (PurchaseRequest request: requests) {
			if (request.getSentTime() == null) {
				requestRepo.deleteById(request.getRequestId());	
			}
		}
		ArrayList<PurchaseRequest> newRequests = new ArrayList<PurchaseRequest>();
		requestRepo.findAll().forEach(newRequests::add);
		Collections.reverse(newRequests);
		model.addAttribute("requests", newRequests);
		return "all_requests";
	}

	@GetMapping("request/delete/{id}")
	public String deleteRequest(@PathVariable("id") Long requestId) {
		requestRepo.deleteById(requestId);
		return "redirect:/request/all";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("ccuser", new CatComUser());
		return "signup";
	}

	@PostMapping("/user/save")
	public String saveUser(@ModelAttribute CatComUser user) {
		ccUserRepository.save(user);
		return "/login";
	}
}

