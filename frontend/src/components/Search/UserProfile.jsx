import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import api, { getPostsbyUserId, getUserProfile } from "../../services/api";
import ProfileHeader from "./ProfileHeader";
import EditProfileModel from "./EditProfileModel";
import UserPosts from "./UserPosts";

const UserProfile = () => {
    const { username } = useParams();
    const [user, setUser] = useState(null);
    const [posts, setPosts] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const [isEditing, setIsEditing] = useState(false);

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                setLoading(true);
                if (!username) throw new Error("Username is missing!");

                const fetchedUser = await getUserProfile(username);
                setUser(fetchedUser);

                const postsResponse = await getPostsbyUserId(fetchedUser.id);
                setPosts(postsResponse);
            } catch (err) {
                console.error("Error fetching user profile:", err);
                setError("Failed to fetch user data.");
            } finally {
                setLoading(false);
            }
        };

        fetchUserData();
    }, [username]);

    if (error) return <p style={{ color: "red" }}>{error}</p>;
    if (loading) return <p>Loading user data...</p>;

    return (
        <div className="profile-container">
            <ProfileHeader
                user={user}
                setIsEditing={setIsEditing}
                loggedInUserId={sessionStorage.getItem("userId")}
            />
            {isEditing && (
                <EditProfileModel
                    user={user}
                    setUser={setUser}
                    setIsEditing={setIsEditing}
                />
            )}
            <UserPosts posts={posts} />
        </div>
    );
};

export default UserProfile;
