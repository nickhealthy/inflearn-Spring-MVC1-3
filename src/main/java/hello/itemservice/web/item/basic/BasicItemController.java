package hello.itemservice.web.item.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;


    /* 모든 상품 조회 */
    @GetMapping
    public String items(Model model) {
        // 모든 상품 조회
        List<Item> items = itemRepository.findAll();
        // 모델에 담기
        model.addAttribute("items", items);
        // 뷰 템플릿 호출
        return "basic/items";
    }

    /* 상풍 상세 */
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /* 상품 등록 폼 */
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /* 상품 등록 폼 */
//    @PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam Integer quantity,
            Model model
    ) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);

        return "basic/item";
    }

    /* 상품 등록 폼 */
//    @PostMapping("/add")
    public String addItemV2(
            @ModelAttribute("item") Item item,
            Model model
    ) {

        itemRepository.save(item);
//        model.addAttribute("item", item); // 자동 추가, 생략 가능

        return "basic/item";
    }

    /* 상품 등록 폼
    * @ModelAttribute의 이름을 생략하면 모델에 저장될 때 클래스명을 사용한다.
    * 이때 클래스의 첫 글자만 소문자로 변경해서 등록한다.
    *  */
//    @PostMapping("/add")
    public String addItemV3(
            @ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /* 상품 등록 폼 */
    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }


    /**
     * 테스트용 데이터 추가
     *
     * @PostConstruct - 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다.
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}
