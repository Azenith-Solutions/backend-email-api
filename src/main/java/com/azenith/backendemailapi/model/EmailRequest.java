package com.azenith.backendemailapi.model;

import lombok.Data;

@Data
public class EmailRequest {
    public String toEmail;
    public String toName;
    public String subject;
    public String content;
}
