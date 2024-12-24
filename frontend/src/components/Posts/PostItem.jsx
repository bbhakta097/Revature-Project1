import React, { useState, useEffect } from "react";
import CommentsSection from "../Comments/CommentSection";
import { getReactionsForPost, hasUserLikedPost, addLike, unLike } from "../../services/api";

const PostItem = ({ post, currentUserId }) => {
    const createdAt = new Date(post.createdAt).toLocaleString();
    const [userLiked, setUserLiked] = useState(null);
    const [likeCount, setLikeCount] = useState(0);

    useEffect(() => {
        const fetchReactions = async () => {
            try {
                const reactions = await getReactionsForPost(post.id);
                setLikeCount(reactions);

                const hasLiked = await hasUserLikedPost(post.id, currentUserId);
                setUserLiked(hasLiked);
            } catch (error) {
                console.error("Error fetching reactions:", error);
            }
        };
        fetchReactions();
    }, [currentUserId, post.id]);

    const toggleLike = async () => {
        try {
            if (userLiked) {
                await unLike(post.id, currentUserId);
                setUserLiked(false);
                setLikeCount((prevCount) => prevCount - 1);
            } else {
                await addLike(post.id, currentUserId, "LIKE");
                setUserLiked(true);
                setLikeCount((prevCount) => prevCount + 1);
            }
        } catch (error) {
            console.error("Error toggling like:", error);
        }
    };

    return (
        <div
            className="post-item"
            key={post.id}
            style={{ border: "1px solid #ccc", margin: "10px", padding: "10px" }}
        >
            <h3>{post.content}</h3>
            <small>Posted by: {post.user?.username || "Unknown User"}</small>
            <small>Posted on: {createdAt}</small>

            <div className="post-actions">
                <small>
                    Likes: {likeCount || 0}
                    <button
                        className="like-btn"
                        onClick={toggleLike}
                        style={{
                            backgroundColor: "transparent",
                            border: "none",
                            cursor: "pointer",
                            fontSize: "1.5em",
                            color: userLiked ? "red" : "black",
                        }}
                        title={userLiked ? "Unlike Post" : "Like Post"}
                    >
                        ♥
                    </button>
                </small>
            </div>

            <br />
            <hr />
            <br />
            <CommentsSection postId={post.id} userId={currentUserId} />
        </div>
    );
};

export default PostItem;
