import React, { useState, useEffect } from 'react';
import CommentsSection from '../Comments/CommentSection';
import { getReactionsForPost, addReaction } from '../../services/api';

const PostItem = ({ post, currentUserId}) => {

    const createdAt = new Date(post.createdAt).toLocaleString(); // Format the date
    const [userReaction, setUserReaction] = useState(0);

    useEffect(() => {
        const fetchReactions = async () => {
            try {
                const reactions = await getReactionsForPost(post.id);
                setUserReaction(reactions);
            } catch (error) {
                console.error("Error fetching reactions:", error);
                setUserReaction(0);
            }
        };
        fetchReactions();
    }, [post.id, currentUserId]);

    const handleReaction = async (reactionType) => {
        try {
            if (userReaction) {
                alert("You have already reacted to this post.");
                return;
            }
            await addReaction(post.id, currentUserId, reactionType);
            setUserReaction({ reactionType });
        } catch (error) {
            console.error("Error adding reaction:", error);
        }
    };

    return (
        <div
            className="post-item"
            key={post.id}
            style={{ border: "1px solid #ccc", margin: "10px", padding: "10px" }}>
            <h3>{post.content}</h3>
            <small>Posted by: {post.user?.username || "Unknown User"}</small>
            <br />
            <small>Posted on: {createdAt}</small>
            <br />
            <small>Likes: </small> 
            {userReaction || 0}
            <button onClick={() => handleReaction("LIKE")}>Like</button>
            <hr />
            <CommentsSection postId={post.id} userId={currentUserId}/>
        </div>
    );
};

export default PostItem;
