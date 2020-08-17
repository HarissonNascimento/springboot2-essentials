package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.APIUser;
import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.APIUserRepository;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class APIUserDetailsService implements UserDetailsService {
    private final APIUserRepository apiUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(apiUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
