package site.metacoding.fileupload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/join")
    public String join(JoinDto joinDto) { // 버퍼로 읽는거 1. json 2. 있는 그대로 받고 싶을 때

        UUID uuid = UUID.randomUUID();

        String requestFileName = joinDto.getFile().getOriginalFilename();
        System.out.println("전송받은 파일명 : " + requestFileName);

        String imgUrl = uuid + "_" + requestFileName;

        // 메모리에 있는 파일데이터를 파일시스템으로 옮겨야 함.
        // 1. 빈 파일을 생성 haha.png
        // 2. 빈 파일에 스트림 연결
        // 3. for문 돌리면서 바이트로 쓰면 됨. FileWriter 객체!!

        try {
            // jar 파일로 구우면 안돌아간다.
            // 주의 1. 폴더가 만들어져 있어야 함.
            // 주의 2. 리눅스는 / 사용. 윈도우는 \ 사용.
            // => 라이브러리가 OS에 맡길수도 있고 자기가 정해줄수도 있으니 /써보고 안되면 \ 써보기
            // 주의 3. 윈도우 경로 : c:/upload/ 리눅스 경로 : /upload/
            // 이번에는 상대경로 사용 예정
            System.out.println("imgUrl : " + imgUrl);
            Path filePath = Paths.get("src/main/resources/static/upload/" + imgUrl);
            Files.write(filePath, joinDto.getFile().getBytes());

            userRepository.save(joinDto.toEntity(imgUrl));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "joinComplete";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @GetMapping("/main")
    public String main(Model model) {
        List<User> users = userRepository.findAll();

        model.addAttribute("user", users.get(0));

        System.out.println("userEntity : " + users.get(0));
        return "main";
    }
}
