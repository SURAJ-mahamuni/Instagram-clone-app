package com.phntechnology.basestructure.model

import com.google.android.exoplayer2.ExoPlayer

data class DemoModel(val name : String)

data class PostData(val isLike : Boolean,val name : String)

data class ReelData(val vName : String,val vUrl : String)

data class ReelModel(
    val title: String,
    val sources: String,
    val description: String
)

class ReelViewModel {
    val reels = listOf(
        ReelModel(
            title = "Big Buck Bunny",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            description = "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge."
        ),
        ReelModel(
            title = "Elephant Dream",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            description = "The first Blender Open Movie from 2006"
        ),
        ReelModel(
            title = "For Bigger Blazes",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            description = "HBO GO now works with Chromecast -- the easiest way to enjoy online video on your TV. For when you want to settle into your Iron Throne to watch the latest episodes. For $35."
        ),
        ReelModel(
            title = "For Bigger Escape",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            description = "Introducing Chromecast. The easiest way to enjoy online video and music on your TV—for when Batman's escapes aren't quite big enough. For $35."
        ),
        ReelModel(
            title = "For Bigger Fun",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            description = "Introducing Chromecast. The easiest way to enjoy online video and music on your TV. For $35.  Find out more at google.com/chromecast."
        ),
        ReelModel(
            title = "For Bigger Joyrides",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            description = "Introducing Chromecast. The easiest way to enjoy online video and music on your TV—for the times that call for bigger joyrides. For $35."
        ),
        ReelModel(
            title = "For Bigger Meltdowns",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            description = "Introducing Chromecast. The easiest way to enjoy online video and music on your TV—for when you want to make Buster's big meltdowns even bigger. For $35."
        ),
        ReelModel(
            title = "Sintel",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            description = "Sintel is an independently produced short film, initiated by the Blender Foundation as a means to further improve and validate the free/open source 3D creation suite Blender."
        ),
        ReelModel(
            title = "Subaru Outback On Street And Dirt",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
            description = "Smoking Tire takes the all-new Subaru Outback to the highest point we can find in hopes our customer-appreciation Balloon Launch will get some free T-shirts into the hands of our viewers."
        ),
        ReelModel(
            title = "Tears of Steel",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            description = "Tears of Steel was realized with crowd-funding by users of the open source 3D creation tool Blender."
        ),
        ReelModel(
            title = "Volkswagen GTI Review",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4",
            description = "The Smoking Tire heads out to Adams Motorsports Park in Riverside, CA to test the most requested car of 2010, the Volkswagen GTI."
        ),
        ReelModel(
            title = "We Are Going On Bullrun",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            description = "The Smoking Tire is going on the 2010 Bullrun Live Rally in a 2011 Shelby GT500, and posting a video from the road every single day!"
        ),
        ReelModel(
            title = "What care can you get for a grand?",
            sources = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",
            description = "The Smoking Tire meets up with Chris and Jorge from CarsForAGrand.com to see just how far $1,000 can go when looking for a car."
        )
    )
}

data class ExoPlayerItem(val exoplayer : ExoPlayer,val position : Int)