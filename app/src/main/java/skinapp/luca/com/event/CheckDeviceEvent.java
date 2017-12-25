package skinapp.luca.com.event;

import skinapp.luca.com.vo.CheckDeviceResponseVo;
import skinapp.luca.com.vo.SignInResponseVo;

public class CheckDeviceEvent {
    private CheckDeviceResponseVo responseVo;

    public CheckDeviceEvent(CheckDeviceResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public CheckDeviceResponseVo getResponse() {
        return responseVo;
    }
}
