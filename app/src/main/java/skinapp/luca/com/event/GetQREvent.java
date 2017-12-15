package skinapp.luca.com.event;

import skinapp.luca.com.vo.GetQRResponseVo;
import skinapp.luca.com.vo.SignUpResponseVo;

public class GetQREvent {
    private GetQRResponseVo responseVo;

    public GetQREvent(GetQRResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public GetQRResponseVo getResponse() {
        return responseVo;
    }
}
