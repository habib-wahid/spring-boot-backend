package com.usba.pss.auth.service;

import com.usba.pss.auth.dto.groupRolePermission.PermissionDto;
import com.usba.pss.auth.dto.custom.AuthorityDto;
import com.usba.pss.auth.dto.custom.JwtUserDto;
import com.usba.pss.auth.dto.UserDto;
import com.usba.pss.auth.model.user.User;
import com.usba.pss.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PermissionService permissionService;

    @Override
    public JwtUserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username).get();
            if (user == null) {
                throw new UsernameNotFoundException("");
            } else {
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(user, userDto);
                List<AuthorityDto> authorityDtoList = new ArrayList<>();
                List<PermissionDto> permissionDtoList = permissionService.getUserPermissions(user.getId());

//                for (UserRole dto:
//                     user.getUserRoles()) {
//                    String userRole = dto.getRole().getName();
//                    authorityDtoList.add(new AuthorityDto(userRole));
//                }

                for (PermissionDto permissionDto:
                     permissionDtoList) {
                    AuthorityDto dto = new AuthorityDto(permissionDto.getName());
                    authorityDtoList.add(dto);
                }
                JwtUserDto jwtUserDto = new JwtUserDto(
                        userDto,
                        authorityDtoList
                );
                return jwtUserDto;
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
