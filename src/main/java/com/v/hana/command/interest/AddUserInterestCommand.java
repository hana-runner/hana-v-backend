package com.v.hana.command.interest;

import com.v.hana.entity.interest.Interest;
import com.v.hana.entity.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddUserInterestCommand {
    public final User user;
    public final Interest interest;
    public final String subtitle, image;

    @Builder
    public AddUserInterestCommand(User user, Interest interest, String subtitle, String image) {
        this.user = user;
        this.interest = interest;
        this.subtitle = subtitle;
        this.image = image;
    }
}
