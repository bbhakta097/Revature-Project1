import React, { useEffect, useState } from "react";
import { followUser, getFollowers, ifFollows, unfollowUser } from '../../services/api';

const ProfileHeader = ({ user, setIsEditing, loggedInUserId }) => {

    const [isFollowing, setIsFollowing] = useState(false);
    const [followers, setFollowers] = useState(0);



    useEffect(() => {
        const fetchUserData = async () => {
            //console.log(user)
            try {
                const followersCount = await getFollowers(user.id);
                setFollowers(followersCount);

                console.log(loggedInUserId);
                console.log(user.id);
                const ifFollow = await ifFollows(loggedInUserId, user.id);
                setIsFollowing(ifFollow);

            } catch (error) {
                console.error('Failed to fetch user data', error);
            }
        };
        fetchUserData();
    }, [loggedInUserId, user.id]);

    const handleUpdateClick = () => {
        if (user.id.toString() !== loggedInUserId) {
            alert("You can only update your own profile.");
            return;
        }
        setIsEditing(true);
    };

    const handleFollow = async () => {
        if (user.id.toString() === loggedInUserId) {
            alert("You cannot follow yourself.");
            return;
        }
        try {
            await followUser(loggedInUserId, user.id);
            setIsFollowing(true);
            setFollowers((prev) => prev + 1);
        } catch (error) {
            console.error('Failed to follow user', error);
        }
    };

    const handleUnfollow = async () => {
        try {
            await unfollowUser(loggedInUserId, user.id);
            setIsFollowing(false);
            setFollowers((prev) => prev - 1);
        } catch (error) {
            console.error('Failed to unfollow user', error);
        }
    };

    return (

        <div>
            <div className="profile-header">
                <h1>{user.username}'s Profile: </h1>
                <br />
                <h3>Bio: {user.bio || "Bio Not Set Yet!"}</h3>
                <br />
                <button className="update-button" onClick={handleUpdateClick}>Update Profile</button>
            </div>

            <div className="profile-followers">
                <span>{followers} Followers</span>
                {!isFollowing ? (
                    <button className="follow-button" onClick={handleFollow}>
                        Follow
                    </button>
                ) : (
                    <button className="unfollow-button" onClick={handleUnfollow}>
                        Unfollow
                    </button>
                )}
            </div>

        </div>

    );
};

export default ProfileHeader;
