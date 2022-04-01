package site.metacoding.fileupload;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoinDto {

    private String username; // form 태그 name = "username"

    private MultipartFile file; // form 태그 name = "file"

    public User toEntity(String imgUrl) {
        User userEntity = new User();

        userEntity.setUsername(username);
        userEntity.setImgUrl(imgUrl);

        return userEntity;
    }
}
