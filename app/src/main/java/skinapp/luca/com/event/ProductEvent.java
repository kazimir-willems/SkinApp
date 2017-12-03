package skinapp.luca.com.event;

import skinapp.luca.com.vo.ProductsResponseVo;
import skinapp.luca.com.vo.SignUpResponseVo;

public class ProductEvent {
    private ProductsResponseVo responseVo;

    public ProductEvent(ProductsResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public ProductsResponseVo getResponse() {
        return responseVo;
    }
}
