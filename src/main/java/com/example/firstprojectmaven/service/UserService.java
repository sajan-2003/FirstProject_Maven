package com.example.firstprojectmaven.service;

import com.example.firstprojectmaven.dto.UserDTO;
import com.example.firstprojectmaven.model.UserModel;
import com.example.firstprojectmaven.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO createUser(UserDTO userDTO) {

        if (userRepo.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException(
                    "A user with this email already exists."
            );
        }

        UserModel userModel = new UserModel();

        userModel.setName(userDTO.getName());
        userModel.setEmail(userDTO.getEmail());
        userModel.setAge(userDTO.getAge());

        UserModel savedUser = userRepo.save(userModel);

        return convertToDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Optional<UserDTO> getUserById(Integer id) {
        return userRepo.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .map(this::convertToDTO);
    }

    public Optional<UserDTO> updateUser(Integer id, UserDTO userDTO) {

        Optional<UserModel> existingUserOptional =
                userRepo.findById(id);

        if (existingUserOptional.isEmpty()) {
            return Optional.empty();
        }

        UserModel existingUser = existingUserOptional.get();

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setAge(userDTO.getAge());

        UserModel updatedUser = userRepo.save(existingUser);

        return Optional.of(convertToDTO(updatedUser));
    }

    public boolean deleteUser(Integer id) {

        if (!userRepo.existsById(id)) {
            return false;
        }

        userRepo.deleteById(id);

        return true;
    }

    private UserDTO convertToDTO(UserModel userModel) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(userModel.getId());
        userDTO.setName(userModel.getName());
        userDTO.setEmail(userModel.getEmail());
        userDTO.setAge(userModel.getAge());

        return userDTO;
    }
}