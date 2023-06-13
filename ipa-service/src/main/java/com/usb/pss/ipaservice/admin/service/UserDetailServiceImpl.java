package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.UserDto;
import com.usb.pss.ipaservice.admin.dto.PermissionDto;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import com.usb.pss.ipaservice.admin.repository.IpaAdminUserRepository;
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

    private final IpaAdminUserRepository userRepository;
    private final PermissionService permissionService;

    @Override
    public JwtUserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            IpaAdminUser user = userRepository.findUserByUsername(username).get();
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
