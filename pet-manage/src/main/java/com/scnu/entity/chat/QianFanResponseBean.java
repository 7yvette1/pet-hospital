package com.scnu.entity.chat;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class QianFanResponseBean {

    private String id;
    private String object;
    private int created;
    private int sentence_id;
    private boolean is_end;
    private boolean is_truncated;
    private String result;
    private boolean need_clear_history;
    private String finish_reason;
    private UsageBean usage;

    public boolean isIs_end() {
        return is_end;
    }

    public void setIs_end(boolean is_end) {
        this.is_end = is_end;
    }

    @NoArgsConstructor
    @Data
    public static class UsageBean {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }
}
