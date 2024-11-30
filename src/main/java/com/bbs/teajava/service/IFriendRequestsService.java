package com.bbs.teajava.service;

import com.bbs.teajava.entity.FriendRequests;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bbs.teajava.utils.ApiResultUtils;

/**
 * <p>
 *  业务类
 * </p>
 *
 * @author hk
 * @since 2024-11-30
 */
public interface IFriendRequestsService extends IService<FriendRequests> {

    /**
     * 申请好用
     *
     * @param friendId 好友ID
     * @param message 申请信息
     * @return 接口结果
     */
    ApiResultUtils applyFriend(Integer friendId, String message);
}
