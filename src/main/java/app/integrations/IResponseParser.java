package app.integrations;

import app.integrations.impl.TailoredCVResponse;

public interface IResponseParser {
    TailoredCVResponse parseResponse(String response);
}


