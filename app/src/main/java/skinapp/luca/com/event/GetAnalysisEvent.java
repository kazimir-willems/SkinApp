package skinapp.luca.com.event;

import skinapp.luca.com.vo.GetAnalysisResponseVo;
import skinapp.luca.com.vo.SignInResponseVo;

public class GetAnalysisEvent {
    private GetAnalysisResponseVo responseVo;

    public GetAnalysisEvent(GetAnalysisResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public GetAnalysisResponseVo getResponse() {
        return responseVo;
    }
}
