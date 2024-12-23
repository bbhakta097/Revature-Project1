import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import api, { getPostsbyUserId, getUserProfile } from "../../services/api";

const UserProfile = () => {
    const { username } = useParams();
    const [user, setUser] = useState(null);
    const [posts, setPosts] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const [isEditing, setIsEditing] = useState(false);
    const [editFields, setEditFields] = useState({ username: "", email: "", password: "", bio: "" });

    const loggedInUserId = sessionStorage.getItem('userId');;


    useEffect(() => {
        const fetchUserData = async () => {
            try {
                setLoading(true); // Set loading state
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
    }, [ username]);

    const handleUpdateClick = () => {
        if (user.id.toString() !== loggedInUserId) {
            alert("You can only update your own profile.");
            console.log(user.id.toString());
            console.log(loggedInUserId)
            return;
        }
        setIsEditing(true);
        setEditFields({
            username: user.username,
            email: user.email,
            password: "",
            bio: user.bio || ""
        });
    };

    const handleUpdateSubmit = async () => {
        try {
            console.log(`Making PUT request to: /users/${user.id}/update`);
            console.log("Payload:", editFields);

            const response = await api.put(`/auth/users/${user.id}/update`, editFields);
            console.log("API Response:", response.data);
            alert("Profile updated successfully.");
            setIsEditing(false);
            //setUser({ ...user, ...editFields });
        } catch (error) {
            console.error("Error updating profile:", error);
            alert(error.response?.data || "Failed to update profile.");
        }
    };

    const handleFieldChange = (e) => {
        const { name, value } = e.target;
        setEditFields((prevFields) => ({ ...prevFields, [name]: value }));
    };

    if (error) return <p style={{ color: "red" }}>{error}</p>;
    if (!user) return <p>Loading user data...</p>;

    return (
        <div>
            <h1>{user.username}'s Profile</h1>
            <p>Bio: {user.bio || "N/A"}</p>

            <button onClick={handleUpdateClick}>Update Profile</button>

            {isEditing && (
                <div className="edit-profile-modal" style={{ border: "1px solid #ccc", padding: "10px", margin: "10px 0" }}>
                    <h2>Edit Profile</h2>
                    <label>
                        Username:
                        <input
                            type="text"
                            name="username"
                            value={editFields.username}
                            onChange={handleFieldChange}
                        />
                    </label>
                    <br />
                    <label>
                        Email:
                        <input
                            type="email"
                            name="email"
                            value={editFields.email}
                            onChange={handleFieldChange}
                        />
                    </label>
                    <br />                                                  
                    <label>
                        Password:
                        <input
                            type="password"
                            name="password"
                            value={editFields.password}
                            onChange={handleFieldChange}
                        />
                    </label>                                                       
                    <br />
                    <label>
                        Bio:
                        <textarea
                            name="bio"
                            value={editFields.bio}
                            onChange={handleFieldChange}
                        />
                    </label>
                    <br />
                    <button onClick={handleUpdateSubmit}>Save Changes</button>
                    <button onClick={() => setIsEditing(false)}>Cancel</button>
                </div>
            )}




            <h2>Posts</h2>
            {posts.length === 0 ? (
                <p>No posts yet.</p>
            ) : (
                posts.map((post) => (
                    <div key={post.id} className="user-post" style={{
                        border: "1px solid #ccc",
                        margin: "10px",
                        padding: "10px",
                    }}>
                        <p>{post.content}</p>
                        <small>
                            Posted on: {new Date(post.createdAt).toLocaleString()} 
                            <br/>
                            <p>Likes: {post.likes}</p>
                        </small>
                       
                    </div>
                ))
            )}
        </div>
    );
};

export default UserProfile;
