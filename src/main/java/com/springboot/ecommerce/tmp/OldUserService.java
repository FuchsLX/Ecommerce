//package com.springboot.ecommerce.tmp;
//
//import com.springboot.ecommerce.tmp.OldUser;
//import com.springboot.ecommerce.tmp.OldUserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//import static com.springboot.ecommerce.constants.ErrorMessage.USER_NOT_FOUND_MESSAGE;
//
//@Service
//@RequiredArgsConstructor
//public class OldUserService implements UserDetailsService {
//    private final OldUserRepository oldUserRepository;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String email)
//            throws UsernameNotFoundException {
//        return oldUserRepository.findByEmail(email)
//                .orElseThrow(() ->
//                        new UsernameNotFoundException(
//                                String.format(USER_NOT_FOUND_MESSAGE, email)
//                        ));
//    }
//    public int enableUser(String email){
//        return oldUserRepository.enableUser(email);
//    }
//
//    public Optional<OldUser> findByEmailUser(String email){
//        return oldUserRepository.findByEmail(email);
//    }
//
//    public OldUser saveUser(OldUser user){
//        return oldUserRepository.save(user);
//    }
//
//    public OldUser findByEmail(String email){return oldUserRepository.findByUsername(email);}
//
//
//}
