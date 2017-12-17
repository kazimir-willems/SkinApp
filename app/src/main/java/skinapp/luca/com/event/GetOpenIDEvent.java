package skinapp.luca.com.event;

import skinapp.luca.com.vo.GetOpenIDResponseVo;

public class GetOpenIDEvent {
    private GetOpenIDResponseVo responseVo;

    public GetOpenIDEvent(GetOpenIDResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public GetOpenIDResponseVo getResponse() {
        return responseVo;
    }
}
