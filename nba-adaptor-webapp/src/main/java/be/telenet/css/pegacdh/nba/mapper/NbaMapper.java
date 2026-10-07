package be.telenet.css.pegacdh.nba.mapper;

import be.telenet.css.pegacdh.nba.cdh.CdhResponse;
import be.telenet.css.pegacdh.nba.dto.NbaDto;
import be.telenet.css.pegacdh.nba.dto.NextBestActionDto;
import org.springframework.stereotype.Component;

import java.util.List;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Component
public class NbaMapper extends AbstractNbaMapper {

    public NbaDto map(CdhResponse cdhResponse, String customerNumber) {
        List<NextBestActionDto> nextBestActionDtos =  getNextBestActions(cdhResponse);

        return isNotEmpty(nextBestActionDtos)
                ? map(nextBestActionDtos, getFirstValidContainer(cdhResponse), customerNumber)
                : getEmptyNbaResponse();
    }
}
