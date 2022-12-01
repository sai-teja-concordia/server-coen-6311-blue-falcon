package com.bluefalcon.project.service;

import com.bluefalcon.project.dao.NewsDao;
import com.bluefalcon.project.dao.UserActivityDao;
import com.bluefalcon.project.dao.UserDao;
import com.bluefalcon.project.dao.UserSocialDao;
import com.bluefalcon.project.enums.FriendRequestStatus;
import com.bluefalcon.project.enums.SavedNewsEnum;
import com.bluefalcon.project.model.News;
import com.bluefalcon.project.model.User;
import com.bluefalcon.project.model.UserActivity;
import com.bluefalcon.project.model.UserSocial;
import com.bluefalcon.project.request.FriendRequest;
import com.bluefalcon.project.request.SavedNewsRequest;
import com.bluefalcon.project.response.BaseResponse;
import com.bluefalcon.project.response.UserSocialResponse;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    NewsDao newsDao;

    @Autowired
    UserSocialDao userSocialDao;

    @Autowired
    UserActivityDao userActivityDao;

    @Autowired
    MongoTemplate mongoTemplate;

    public User addUser(User user) {
        user.setCreated(System.currentTimeMillis());
        user.setUpdated(System.currentTimeMillis());
        User savedUser = userDao.save(user);

        UserSocial userSocial = new UserSocial();
        userSocial.setUserId(savedUser.getId());
        userSocialDao.save(userSocial);

        UserActivity userActivity = new UserActivity();
        userActivity.setUserId(savedUser.getId());
        userActivityDao.save(userActivity);

        return savedUser;
    }

    public List<User> getUsersNearMe(String location){
        Query fetchQuery = new Query();
        fetchQuery.addCriteria(Criteria.where("location").is(location));
        return mongoTemplate.find(fetchQuery, User.class);
    }

    public Boolean addFavouriteTopic(User user){
        Query fetchQuery = new Query();
        fetchQuery.addCriteria(Criteria.where("emailId").is(user.getEmailId()));
        Update update = new Update();
        update.set("userInterests", user.getUserInterests());
        mongoTemplate.updateFirst(fetchQuery, update, User.class);
        return true;
    }

    public User getUser(String emailId) {
        return userDao.findByEmailId(emailId);
    }

    public User updateUser(User user) {
        Query updateQuery = new Query();
        updateQuery.addCriteria(Criteria.where("emailId").is(user.getEmailId()));
        Update update = new Update();
        if (user.getName() != null) {
            update.set("name", user.getName());
        }
        if (user.getLocation() != null) {
            update.set("location", user.getLocation());
        }
        if (user.getImageUrl() != null) {
            update.set("imageUrl", user.getImageUrl());
        }
        if (user.getUserInterests() != null) {
            update.set("userInterests", user.getUserInterests());
        }
        mongoTemplate.updateFirst(updateQuery, update, User.class);
        return user;
    }


    public UserSocialResponse getUserSocial(String userId) {

        UserSocial userSocial = userSocialDao.findByUserId(userId);
        Set<String> allUserIds = new HashSet<>();
        Set<String> friendUserIds = userSocial.getFriends();
        Set<String> followerUserIds = userSocial.getFollowerUsers();
        Set<String> followingUserIds = userSocial.getFollowingUsers();
        Set<String> receivedFriendRequests = userSocial.getReceivedFriendRequests();
        Set<String> sentFriendRequests = userSocial.getReceivedFriendRequests();
        Set<String> blockedUserIds = userSocial.getBlockedUsers();
        allUserIds.addAll(friendUserIds);
        allUserIds.addAll(followerUserIds);
        allUserIds.addAll(followingUserIds);
        allUserIds.addAll(blockedUserIds);
        allUserIds.addAll(receivedFriendRequests);
        allUserIds.addAll(sentFriendRequests);

        Iterable<User> allUsers = userDao.findAllById(allUserIds);
        List<User> friends = new ArrayList<>();
        List<User> followers = new ArrayList<>();
        List<User> following = new ArrayList<>();
        List<User> blockedUsers = new ArrayList<>();
        List<User> receivedRequests = new ArrayList<>();
        List<User> sentRequests = new ArrayList<>();

        allUsers.forEach(e -> {
            if (friendUserIds.contains(e.getId())){
                friends.add(e);
            } else if (followerUserIds.contains(e.getId())){
                followers.add(e);
            } else if (followingUserIds.contains(e.getId())){
                following.add(e);
            } else if (blockedUserIds.contains(e.getId())){
                blockedUsers.add(e);
            } else if (receivedFriendRequests.contains(e.getId())){
                receivedRequests.add(e);
            }  else if (sentFriendRequests.contains(e.getId())){
                sentRequests.add(e);
            }
        });
        return UserSocialResponse.builder()
                .friends(friends)
                .followers(followers)
                .following(following)
                .blocked(blockedUsers)
                .receivedRequests(receivedRequests)
                .sentRequests(sentRequests)
                .build();
    }

    public List<User> getUserFriends(String userId) {
        UserSocial userSocial = userSocialDao.findByUserId(userId);
        Set<String> friendUserIds = userSocial.getFriends();
        Iterable<User> friendsIter = userDao.findAllById(friendUserIds);
        List<User> friends = new ArrayList<>();
        friendsIter.forEach(friends::add);
        return friends;
    }

    public BaseResponse sendingFriendRequest(FriendRequest friendRequest) {
        String toUserId = friendRequest.getToUserId();
        String fromUserId = friendRequest.getFromUserId();
        UserSocial toUser = userSocialDao.findByUserId(toUserId);
        UserSocial fromUser = userSocialDao.findByUserId(fromUserId);
        toUser.getReceivedFriendRequests().add(fromUserId);
        fromUser.getSentFriendRequests().add(toUserId);
        userSocialDao.save(toUser);
        userSocialDao.save(fromUser);
        return BaseResponse.builder().message("Success").build();
    }

    public BaseResponse actionFriendRequest(FriendRequest friendRequest) {
        if (FriendRequestStatus.ACCEPTED.equals(friendRequest.getStatus())){
            String toUserId = friendRequest.getToUserId();
            String fromUserId = friendRequest.getFromUserId();
            UserSocial toUser = userSocialDao.findByUserId(toUserId);
            UserSocial fromUser = userSocialDao.findByUserId(fromUserId);
            toUser.getReceivedFriendRequests().remove(fromUserId);
            toUser.getFriends().add(fromUserId);
            fromUser.getFriends().add(toUserId);
            fromUser.getSentFriendRequests().remove(toUserId);
            userSocialDao.save(toUser);
            userSocialDao.save(fromUser);
            return BaseResponse.builder().message("Success").build();
        } else if (FriendRequestStatus.REJECTED.equals(friendRequest.getStatus())){
            String toUserId = friendRequest.getToUserId();
            String fromUserId = friendRequest.getFromUserId();
            UserSocial toUser = userSocialDao.findByUserId(toUserId);
            UserSocial fromUser = userSocialDao.findByUserId(fromUserId);
            toUser.getReceivedFriendRequests().remove(fromUserId);
            fromUser.getSentFriendRequests().remove(toUserId);
            userSocialDao.save(toUser);
            userSocialDao.save(fromUser);
            return BaseResponse.builder().message("Success").build();
        } else if (FriendRequestStatus.UNFRIEND.equals(friendRequest.getStatus())){
            String toUserId = friendRequest.getToUserId();
            String fromUserId = friendRequest.getFromUserId();
            UserSocial toUser = userSocialDao.findByUserId(toUserId);
            UserSocial fromUser = userSocialDao.findByUserId(fromUserId);
            toUser.getFriends().remove(fromUserId);
            fromUser.getFriends().remove(toUserId);
            userSocialDao.save(toUser);
            userSocialDao.save(fromUser);
            return BaseResponse.builder().message("Success").build();
        }
        return null;
    }

    public User getUserById(String userId) {
        User user = userDao.findById(userId).orElse(null);
        UserSocial userSocial = userSocialDao.findByUserId(userId);
        Set<String> allUserIds = new HashSet<>();
        Set<String> friendUserIds = userSocial.getFriends();
        Set<String> followerUserIds = userSocial.getFollowerUsers();
        Set<String> followingUserIds = userSocial.getFollowingUsers();
        Set<String> blockedUserIds = userSocial.getBlockedUsers();
        Set<String> sentFriendRequests = userSocial.getSentFriendRequests();
        allUserIds.addAll(friendUserIds);
        allUserIds.addAll(followerUserIds);
        allUserIds.addAll(followingUserIds);
        allUserIds.addAll(blockedUserIds);
        allUserIds.addAll(sentFriendRequests);

        Iterable<User> allUsers = userDao.findAllById(allUserIds);
        List<User> friends = new ArrayList<>();
        List<User> followers = new ArrayList<>();
        List<User> following = new ArrayList<>();
        List<User> blockedUsers = new ArrayList<>();
        List<User> sentRequests = new ArrayList<>();
        allUsers.forEach(e -> {
            if (friendUserIds.contains(e.getId())){
                friends.add(e);
            } else if (followerUserIds.contains(e.getId())){
                followers.add(e);
            } else if (followingUserIds.contains(e.getId())){
                following.add(e);
            } else if (blockedUserIds.contains(e.getId())){
                blockedUsers.add(e);
            } else if (sentFriendRequests.contains(e.getId())){
                sentRequests.add(e);
            }
        });
        user.setFriends(friends);
        user.setFollowers(followers);
        user.setFollowing(following);
        user.setBlocked(blockedUsers);
        user.setSentFriendRequests(sentRequests);
        UserActivity userActivity = userActivityDao.findByUserId(userId);
        if(userActivity != null){
            List<String> favouriteNewsIds = userActivity.getFavouriteNews();
            if (!CollectionUtils.isEmpty(favouriteNewsIds)){
                Iterable<News> favNews = newsDao.findAllById(favouriteNewsIds);
                List<News> favouriteNews = new ArrayList<>();
                favNews.forEach(favouriteNews::add);
                user.setSavedNews(favouriteNews);
            }
        }
        return user;
    }

    public List<News> getWishlist(String emailId){
        User user = userDao.findByEmailId(emailId);
        return user.getWishlist();
    }

    public List<News> removeNewsFromWishlist(String emailId, String newsId){
        User user = userDao.findByEmailId(emailId);
        Optional<News> news = newsDao.findById(newsId);
        List<News> tempList = user.getWishlist();
        if (news.isPresent() && user != null) {
            int index=-1;
                tempList.remove(news.get());

            user.setWishlist(tempList);
            userDao.save(user);
        }
        return user.getWishlist();
    }

    public List<News> addNewsToWishlist(String emailId, String newsId){

        User user = userDao.findByEmailId(emailId);
        Optional<News> news = newsDao.findById(newsId);
        if (news.isPresent() && user != null) {
            List<News> tempList = user.getWishlist();
            if (tempList == null) {
                tempList = new ArrayList<News>();
            }
            tempList.add(news.get());
            user.setWishlist(tempList);
            userDao.save(user);
        }
        return user.getWishlist();
    }

    public BaseResponse updateSavedNewsList(SavedNewsRequest savedNewsRequest){
        if (SavedNewsEnum.BOOKMARKED.equals(savedNewsRequest.getSavedNewsEnum())){
            UserActivity userActivity = userActivityDao.findByUserId(savedNewsRequest.getUserId());
            List<String> favouriteNews = userActivity.getFavouriteNews();
            if (CollectionUtils.isEmpty(favouriteNews)){
                favouriteNews = new ArrayList<>();
            }
            favouriteNews.add(savedNewsRequest.getNewsId());
            userActivity.setFavouriteNews(favouriteNews);
            userActivityDao.save(userActivity);
            return BaseResponse.builder().message("Success").build();
        } else if (SavedNewsEnum.UN_BOOKMARKED.equals(savedNewsRequest.getSavedNewsEnum())){
            UserActivity userActivity = userActivityDao.findByUserId(savedNewsRequest.getUserId());
            List<String> favouriteNews = userActivity.getFavouriteNews();
            if (CollectionUtils.isEmpty(favouriteNews)){
                favouriteNews = new ArrayList<>();
            }
            favouriteNews.remove(savedNewsRequest.getNewsId());
            userActivity.setFavouriteNews(favouriteNews);
            userActivityDao.save(userActivity);
            return BaseResponse.builder().message("Success").build();
        } else {
            return  null;
        }
    }


    public List<User> searchUsers(String query) {
        List<User> allUsers = userDao.findAll();
        List<User> filteredUsers = allUsers.stream().filter(user -> {
            int ratio = FuzzySearch.partialRatio(user.getName().toLowerCase(Locale.ROOT) , query.toLowerCase(Locale.ROOT));
            return ratio >= 80;
        }).collect(Collectors.toList());
        return filteredUsers;
    }
}
