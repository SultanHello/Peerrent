package com.example.Peerrent.services;

import com.example.Peerrent.modules.*;
import com.example.Peerrent.modules.DTO.ItemDTO;
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
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
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
        int time = item.getTime();
        if(item!=null){
            if(item.getTimeType().equals(Time.HOUR)){
                client.getRentItems().add(item);
                item.setClient(client);
                item.setStartRent(new Date(System.currentTimeMillis()));
                item.setEndRent(new Date(System.currentTimeMillis()+ (long) time *60*60*1000));
                itemRepository.save(item);
                userRepository.save(client);
                return "Success";

            }else if(item.getTimeType().equals(Time.DAY)){
                client.getRentItems().add(item);
                item.setClient(client);
                item.setStartRent(new Date(System.currentTimeMillis()));
                item.setEndRent(new Date(System.currentTimeMillis()+(long) time*24*60*60*60));
                itemRepository.save(item);
                userRepository.save(client);
                return "Success";

            }else{
                client.getRentItems().add(item);
                item.setClient(client);
                item.setStartRent(new Date(System.currentTimeMillis()));
                item.setEndRent(new Date(LocalDateTime.now()
                        .plus(Period.ofMonths(item.getTime()))
                        .atZone(ZoneId.of("UTC"))
                        .toInstant()
                        .toEpochMilli()));
                itemRepository.save(item);
                userRepository.save(client);
                return "Success";
            }

        }



        return "error not found";

    }

}
