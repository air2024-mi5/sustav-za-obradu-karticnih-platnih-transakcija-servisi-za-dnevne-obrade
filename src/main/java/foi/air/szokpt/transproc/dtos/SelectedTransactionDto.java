package foi.air.szokpt.transproc.dtos;

import java.util.UUID;

public class SelectedTransactionDto {
    private UUID guid;
    private String selectedBy;

    public SelectedTransactionDto(UUID guid, String selectedBy) {
        this.guid = guid;
        this.selectedBy = selectedBy;
    }

    public String getSelectedBy() {
        return selectedBy;
    }

    public void setSelectedBy(String selectedBy) {
        this.selectedBy = selectedBy;
    }

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }
}
