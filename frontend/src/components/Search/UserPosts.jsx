import React from "react";

const UserPosts = ({ posts }) => {
    return (
        <div className="user-post">
            <h2>Posts</h2>
            {posts.length === 0 ? (
                <p>No posts yet.</p>
            ) : (
                posts.map((post) => (
                    <div
                        key={post.id}
                        className="user-post"
                        style={{
                            border: "1px solid #ccc",
                            margin: "10px",
                            padding: "10px",
                        }}
                    >
                        <p>{post.content}</p>
                        <small>
                            Posted on: {new Date(post.createdAt).toLocaleString()}
                            <br />
                            <p>Likes: {post.likes}</p>
                        </small>
                    </div>
                ))
            )}
        </div>
    );
};

export default UserPosts;
