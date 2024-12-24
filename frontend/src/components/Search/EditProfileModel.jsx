import React, { useState } from "react";
import api from "../../services/api";

const EditProfileModel = ({ user, setUser, setIsEditing }) => {
    const [editFields, setEditFields] = useState({
        username: user.username,
        email: user.email,
        password: "",
        bio: user.bio || "",
    });

    const handleFieldChange = (e) => {
        const { name, value } = e.target;
        setEditFields((prevFields) => ({ ...prevFields, [name]: value }));
    };

    const handleUpdateSubmit = async () => {
        try {
            const response = await api.put(`/auth/users/${user.id}/update`, editFields);
            alert("Profile updated successfully.");
            setUser({ ...user, ...editFields });
            setIsEditing(false);
        } catch (error) {
            console.error("Error updating profile:", error);
            alert(error.response?.data || "Failed to update profile.");
        }
    };

    return (
        <div className="edit-profile-model" style={{ border: "1px solid #ccc", padding: "10px", margin: "10px 0" }}>
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
            <button className="save-button" onClick={handleUpdateSubmit}>Save Changes</button>
            <button className="cancel-button" onClick={() => setIsEditing(false)}>Cancel</button>
        </div>
    );
};

export default EditProfileModel;
