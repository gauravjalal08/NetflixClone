package com.example.apinetflix.Service;

import com.example.apinetflix.Accessor.Model.ProfileType;
import com.example.apinetflix.Accessor.Model.UserDTO;
import com.example.apinetflix.Accessor.ProfileAccessor;
import com.example.apinetflix.Controller.Model.ProfileTypeInput;
import com.example.apinetflix.Exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ProfileService {
    @Autowired
    ProfileAccessor profileAccessor;
    //called by controller layer(activateProfile)
    public void activateProfile(final String name, final ProfileTypeInput type){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        if(name.length()<5 || name.length()>20){
            throw new InvalidDataException("Name length between 5 to 20");
        }
        profileAccessor.addNewProfile(userDTO.getUserId(),name, ProfileType.valueOf(type.name()));
    }
    public  void deactivateProfile(final String profileId){
        profileAccessor.deleteProfile(profileId);
    }
}
