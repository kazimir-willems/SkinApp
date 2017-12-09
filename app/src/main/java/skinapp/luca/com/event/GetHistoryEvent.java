package skinapp.luca.com.event;

import skinapp.luca.com.vo.HistoryResponseVo;
import skinapp.luca.com.vo.ProductsResponseVo;

public class GetHistoryEvent {
    private HistoryResponseVo responseVo;

    public GetHistoryEvent(HistoryResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public HistoryResponseVo getResponse() {
        return responseVo;
    }
}
