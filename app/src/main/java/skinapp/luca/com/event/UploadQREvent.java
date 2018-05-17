package skinapp.luca.com.event;

import skinapp.luca.com.vo.SignUpResponseVo;
import skinapp.luca.com.vo.UploadQRResponseVo;

public class UploadQREvent {
    private UploadQRResponseVo responseVo;

    public UploadQREvent(UploadQRResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public UploadQRResponseVo getResponse() {
        return responseVo;
    }
}
