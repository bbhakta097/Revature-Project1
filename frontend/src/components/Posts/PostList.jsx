import React from 'react';
import PostItem from './PostItem';

const PostList = ({ posts, currentUserId }) => {
    if (!posts.length) {
        return <p>No posts available.</p>;
    }

    return (
        <div>
            {posts.map((post) => (
                <PostItem key={post.id} post={post} currentUserId={currentUserId}/>
            ))}
        </div>
    );
};

export default PostList;
