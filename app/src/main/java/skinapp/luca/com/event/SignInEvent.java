package skinapp.luca.com.event;

import skinapp.luca.com.vo.SignInResponseVo;

public class SignInEvent {
    private SignInResponseVo responseVo;

    public SignInEvent(SignInResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public SignInResponseVo getResponse() {
        return responseVo;
    }
}
