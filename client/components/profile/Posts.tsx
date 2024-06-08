"use client";

import React, { SetStateAction, useState } from "react";
import { userProfileViewer } from "../context/UserProfileProvider";
import { ChildNodeProps, ClassNameProp } from "@/lib/interface";
import { cn } from "@/lib/utils";
import { ArrowUpTrayIcon, PlusCircleIcon } from "@heroicons/react/24/outline";
import { Button } from "../ui/button";
import { UserPost } from "@prisma/client";
import { Dialog, DialogTrigger } from "../ui/dialog";
import PostCreateDialog from "../creator/PostCreateDialog";
const Posts = () => {
  const { userPosts } = userProfileViewer();
  const [openDialog, setOpenDialog] = useState<boolean>(false);
  const { userPosts: posts, loading } = userPosts;
  return (
    <Dialog open={openDialog} onOpenChange={setOpenDialog}>
      <div className="w-full">
        {posts.length > 0 && (
          <DialogTrigger asChild>
            <Button
              variant={"outline"}
              onClick={() => setOpenDialog(true)}
              className="h-[5rem] border-2 my-4 rounded-sm w-full  flex justify-center items-center hover:bg-accent transition-colors"
            >
              <PlusCircleIcon className="w-7 h-7" />
              <p className="text-xl ml-2">Upload a new post</p>
            </Button>
          </DialogTrigger>
        )}

        {posts.length === 0 ? (
          <EmptyPostTemplate dispatch={setOpenDialog} />
        ) : (
          <UserPosts posts={posts} />
        )}
      </div>
      <PostCreateDialog dispatch={setOpenDialog} />
    </Dialog>
  );
};

interface TileProps extends ChildNodeProps, ClassNameProp {}
const PostTile = ({ children, className }: TileProps) => {
  return (
    <div
      className={cn(
        "aspect-[4/3] md:basis-1/3 lg:basis-1/4 mx-2 my-2 flex-grow flex-shrink min-w-[18rem] w-full border-2 rounded-sm",
        className
      )}
    >
      {children}
    </div>
  );
};

const opacityTransition = [
  "bg-inverted-foreground/15",
  "bg-inverted-foreground/[12.5%]",
  "bg-inverted-foreground/10",
  "bg-inverted-foreground/[7.5%]",
  "bg-inverted-foreground/5",
];

interface UserPostsProps {
  posts: UserPost[];
}

const UserPosts = (posts: UserPostsProps) => {
  return (
    <div className="flex flex-wrap justify-center relative my-4 w-full">
      {posts.posts.map((post, index) => (
        <PostTile key={post.id}>
          {/* <div className="w-full h-full flex justify-center items-center">
            <img
              src={post.thumbnail}
              alt={post.title}
              className="w-full h-full object-cover"
            />
          </div> */}
        </PostTile>
      ))}
    </div>
  );
};
interface Props {
  dispatch: React.Dispatch<SetStateAction<boolean>>;
}

const EmptyPostTemplate = ({ dispatch }: Props) => {
  return (
    <div className="flex flex-wrap justify-center relative my-4 w-full">
      <PostTile className="border-dashed flex flex-col justify-center items-center ">
        <ArrowUpTrayIcon className="w-10 h-10" />
        <h2 className="text-lg lg:text-xl font-semibold mt-4 capitalize">
          Upload your first creation
        </h2>
        <h3 className="text-xs lg:text-sm text-secondary-header mt-2 text-center mx-8">
          Flex your skills as a developer and be apart of a growing community
        </h3>

        <DialogTrigger asChild>
          <Button
            className="mt-6"
            variant={"outline"}
            onClick={() => dispatch(true)}
          >
            Create a New Post
          </Button>
        </DialogTrigger>
      </PostTile>
      {Array.from({ length: 5 }).map((_, index) => (
        <PostTile
          key={`empty-post-${index}`}
          className={cn(`border-transparent`, opacityTransition[index])}
        >
          <></>
        </PostTile>
      ))}
    </div>
  );
};

export default Posts;