package

import com.mobydigital.segunda.evaluacion.model.PoliticalParty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CandidateDto {

    private String fullName;

    private PoliticalPartyDto partyDto;

    private List<VoteDto> votesDto = new ArrayList<>();
}
