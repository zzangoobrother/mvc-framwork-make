package com.example.mvcframworkmake.springmvcFinal.web.basic;

import com.example.mvcframworkmake.springmvcFinal.domain.item.Item;
import com.example.mvcframworkmake.springmvcFinal.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName, @RequestParam int price, @RequestParam Integer quantity, Model model) {
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }
    
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        itemRepository.save(item);
        return "basic/item";
    }
    
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }
    
    @GetMapping("/{itemId}/edit")
    public String editFrom(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }


    
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute("item") Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /*
        테스트용 데이터
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
