package skinapp.luca.com.event;

import skinapp.luca.com.vo.GetDeviceQRResponseVo;
import skinapp.luca.com.vo.UploadQRResponseVo;

public class GetDeviceQREvent {
    private GetDeviceQRResponseVo responseVo;

    public GetDeviceQREvent(GetDeviceQRResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public GetDeviceQRResponseVo getResponse() {
        return responseVo;
    }
}
