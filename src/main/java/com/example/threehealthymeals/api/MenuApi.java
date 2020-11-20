package com.example.threehealthymeals.api;

import com.example.threehealthymeals.domain.restaurant.Menu;
import com.example.threehealthymeals.domain.restaurant.MenuRepository;
import com.example.threehealthymeals.service.MenuService;
import com.example.threehealthymeals.web.dto.menu.MenuCreateRequest;
import com.example.threehealthymeals.web.dto.menu.MenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/menus")
@RestController
public class MenuApi {

    private final MenuService menuService;
    private final MenuRepository menuRepository;

    @PostMapping
    public ResponseEntity<MenuResponse> create(@RequestBody MenuCreateRequest request){
        return Optional
                .ofNullable(menuService.create(request))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateUrl(@PathVariable Long id, @RequestBody String imgUrl){
        Optional<Menu> byId = menuRepository.findById(id);
        if(!byId.isPresent()){
            return (ResponseEntity<?>) ResponseEntity.notFound();
        }
        Menu menu = byId.get();
        menu.updateImgUrl(imgUrl);
        return ResponseEntity.ok(menu);
    }
}
