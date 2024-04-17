package utils;

import dto.IndexedUserDTO;
import model.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserIndexer {
    public static List<IndexedUserDTO> indexUsers(List<User> users) {
        return IntStream.range(0, users.size())
                .mapToObj(index -> {
                    User user = users.get(index);
                    return new IndexedUserDTO(index + 1, user.getUserId(), user.getName(), user.getEmail());
                })
                .collect(Collectors.toList());
    }
}
