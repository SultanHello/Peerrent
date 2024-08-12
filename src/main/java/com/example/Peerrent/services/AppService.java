package com.example.Peerrent.services;

import com.example.Peerrent.modules.DTO.ItemDTO;
import com.example.Peerrent.modules.Item;
import com.example.Peerrent.modules.RegUser;
import com.example.Peerrent.modules.Role;
import com.example.Peerrent.modules.User;
import com.example.Peerrent.repositories.ItemRepository;
import com.example.Peerrent.repositories.UserRepository;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;

@AllArgsConstructor
@Service
public class AppService {

    @Autowired
    private final UserRepository userRepository;
    private ConvertService convertService;

    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    public String regUser(RegUser regUser){
        User user= userRepository.save(User.builder()
                        .name(regUser.getName())
                        .surname(regUser.getSurname())
                        .email(regUser.getEmail())
                        .password(passwordEncoder.encode(regUser.getPassword()))
                        .city(regUser.getCity())
                        .role(Role.DEFAULT)
                .build());
        return jwtService.generateToken(user);
    }


    public String getName(String name) {
        return userRepository.findByEmail(name).getName();
    }

    public ItemDTO addItem(ItemDTO itemDTO){
        itemRepository.save(Item.builder()
                        .nameItem(itemDTO.getNameItem())
                        .owner(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()))
                        .price(itemDTO.getPriceInTime()*itemDTO.getTime())
                        .timeType(itemDTO.getTimeType())
                        .time(itemDTO.getTime())
                        .priceInTime(itemDTO.getPriceInTime())
                        .info(itemDTO.getInfo())

                .build());
        itemDTO.setPrice(itemDTO.getPriceInTime()*itemDTO.getTime());
        itemDTO.setOwner(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getName()+" "+
                userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getSurname());
        return itemDTO;
    }
    public List<ItemDTO> lists(){
        return convertService.ListToDtoList(itemRepository.findAll());


    }
    public String rentItem(Long id){
        User client =userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Item item = itemRepository.findById(id).orElse(null);
        client.getRentItems().add(item);
        if (item != null) {
            client.getRentItems().add(item);
            item.setClient(client);
            itemRepository.save(item);
            userRepository.save(client);
            return "Success";
        }

        return "error not found";

    }

}
