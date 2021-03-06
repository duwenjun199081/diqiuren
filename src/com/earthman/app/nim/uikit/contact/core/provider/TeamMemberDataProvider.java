package com.earthman.app.nim.uikit.contact.core.provider;

import java.util.ArrayList;
import java.util.List;

import com.earthman.app.nim.uikit.cache.TeamDataCache;
import com.earthman.app.nim.uikit.contact.core.item.AbsContactItem;
import com.earthman.app.nim.uikit.contact.core.item.ContactItem;
import com.earthman.app.nim.uikit.contact.core.item.ItemTypes;
import com.earthman.app.nim.uikit.contact.core.model.ContactGroupStrategy;
import com.earthman.app.nim.uikit.contact.core.model.TeamMemberContact;
import com.earthman.app.nim.uikit.contact.core.query.TextComparator;
import com.earthman.app.nim.uikit.contact.core.query.TextQuery;
import com.netease.nimlib.sdk.team.model.TeamMember;

/**
 * 群成员数据源提供者
 * <p/>
 * Created by huangjun on 2015/5/4.
 */
public class TeamMemberDataProvider {
    public static final List<AbsContactItem> provide(TextQuery query, String tid) {
        List<TeamMemberContact> sources = query(query, tid);
        List<AbsContactItem> items = new ArrayList<AbsContactItem>(sources.size());
        for (TeamMemberContact t : sources) {
            items.add(createTeamMemberItem(t));
        }

        return items;
    }

    private static AbsContactItem createTeamMemberItem(TeamMemberContact teamMember) {
        return new ContactItem(teamMember, ItemTypes.TEAM_MEMBER) {
            @Override
            public int compareTo(ContactItem item) {
                return compareTeamMember((TeamMemberContact) getContact(), (TeamMemberContact) (item.getContact()));
            }

            @Override
            public String belongsGroup() {
                return ContactGroupStrategy.GROUP_TEAM;
            }
        };
    }

    private static int compareTeamMember(TeamMemberContact lhs, TeamMemberContact rhs) {
        return TextComparator.compareIgnoreCase(lhs.getDisplayName(), rhs.getDisplayName());
    }

    /**
     * * 数据查询
     */
    private static final List<TeamMemberContact> query(TextQuery query, String tid) {
        List<TeamMember> teamMembers = TeamDataCache.getInstance().getTeamMemberList(tid);

        List<TeamMemberContact> contacts = new ArrayList<TeamMemberContact>();
        for (TeamMember t : teamMembers) {
            if (t != null && (query == null || ContactSearch.hitTeamMember(t, query))) {
                contacts.add(new TeamMemberContact(t));
            }
        }

        return contacts;
    }
}
