import React, { useEffect, useState } from "react";
import api, { addPost, getAllPosts } from "../services/api";
import { useNavigate } from 'react-router-dom';
import PostList from "./Posts/PostList";
import PostForm from "./Posts/PostForm";
import SearchBar from "./Search/SearchBar";

const Feed = () => {
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const userId = sessionStorage.getItem('userId');


  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const data = await getAllPosts();
        setPosts(data);
      } catch (err) {
        setError("Error fetching posts:");
      }
    };

    fetchPosts();
  }, []);

  const handleAddPost = async (content) => {
    try {
        const userId = sessionStorage.getItem('userId');
        if (!userId) {
            setError('User not logged in.');
            return;
        }
        const response = addPost(content, userId);
        setPosts([response, ...posts]); // Prepend the new post to the feed

        navigate(0);
        
    } catch (err) {
        setError('Failed to add post.');
    }
  };

  return (
    <div>
      <h1>Feed</h1>
      <SearchBar />
      <PostForm onAddPost={handleAddPost} />
      {error && <p style={{ color: "red" }}>{error}</p>}
      <PostList posts={posts} currentUserId={userId}/>
    </div>
  );
};

export default Feed;

/*<h2>Feed</h2>
            {posts.length === 0 ? (
                <p>No posts available</p>
            ) : (
                posts.map(post => (
                    <div key={post.id} style={{ border: '1px solid #ccc', margin: '10px', padding: '10px' }}>
                        <h4>{post.user.username}</h4>
                        <p>{post.content}</p>
                        <small>Created at: {new Date(post.createdAt).toLocaleString()}</small>
                    </div>
                ))
            )}
        </div>*/
