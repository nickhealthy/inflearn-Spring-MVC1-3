package hello.itemservice.web.item.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 테스트용 데이터 추가
     * @PostConstruct
     * - 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다.
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}
