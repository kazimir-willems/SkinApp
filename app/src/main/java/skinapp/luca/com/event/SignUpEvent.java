package skinapp.luca.com.event;

import skinapp.luca.com.vo.SignUpResponseVo;

public class SignUpEvent {
    private SignUpResponseVo responseVo;

    public SignUpEvent(SignUpResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public SignUpResponseVo getResponse() {
        return responseVo;
    }
}
