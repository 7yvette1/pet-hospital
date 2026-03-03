package com.scnu.entity.system;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xuzhijun
 * @date 2024年05月21日 15:12
 */
@Data
public class SysIcon implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * icon编号
     */
    private Integer iconId;

    /**
     * icon名称
     */
    private String iconName;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysIcon other = (SysIcon) that;
        return (this.getIconId() == null ? other.getIconId() == null : this.getIconId().equals(other.getIconId()))
            && (this.getIconName() == null ? other.getIconName() == null : this.getIconName().equals(other.getIconName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIconId() == null) ? 0 : getIconId().hashCode());
        result = prime * result + ((getIconName() == null) ? 0 : getIconName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", iconId=").append(iconId);
        sb.append(", iconName=").append(iconName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}