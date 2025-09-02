# TravelTogether: Multi-Device Collaborative Trip Planning System

## Overview

TravelTogether is a collaborative Android application designed to investigate information synchronization methods in large display-centered multi-device environments. The system enables two users to collaboratively plan travel itineraries using both personal mobile devices and a shared large display.

## Research Context

This project was developed as part of a research study examining how different information-sharing methods impact user collaboration, task efficiency, and user experience in multi-device environments. The study specifically focuses on three synchronization modes and their effects on both familiar and unfamiliar user pairs.

## System Architecture

### Hardware Setup

- **Personal Devices**: 2x Redmi 12C smartphones (6.71 inch) acting as client devices
- **Shared Display**: Vivo Pad Air tablet (11.5 inch) connected to 65-inch Hisense large screen
- **Communication**: WebSocket protocol in server-client model for real-time synchronization

### Software Components

- Android application with three distinct information synchronization modes
- Real-time map-based interface using Google Maps integration
- Comprehensive logging system for user interactions and viewport changes
- Multi-device communication protocol for seamless data sharing

## Information Synchronization Modes

### 1. No Sharing

- Users can only see attractions they've added on their own devices
- Partner's contributions visible only on the shared large screen
- Promotes independent exploration with centralized overview

### 2. Conditional Sharing

- Users can view each other's contributions when their viewports overlap
- Context-aware information synchronization during collaboration
- Facilitates awareness during focused discussions on specific areas

### 3. Continuous Sharing

- Real-time sharing of all partner activities across personal devices
- Minimized overview on mobile devices shows complete itinerary
- Maintains constant mutual awareness between collaborators

## Features

### Core Functionality

- Interactive map exploration with zoom and pan capabilities
- Attraction categorization (outdoor, indoor, shopping, hotels) with distinct icons
- Drag-and-drop itinerary planning with time constraints
- Real-time collaboration with visual indicators of partner activities
- Comprehensive route optimization to minimize backtracking

### User Interface

- **Personal Devices**: Detailed exploration and interaction interface
- **Shared Display**: Overview map with complete itinerary and colored viewport indicators
- **Progress Tracking**: Real-time display of remaining travel time
- **Visual Feedback**: Partner activities indicated through colored flags and arrows

## Installation & Setup

### Prerequisites

- Android development environment
- Three Android devices (2 smartphones, 1 tablet)
- Large display with HDMI connectivity
- Wi-Fi network for device communication

### Configuration

- **Server Setup**: Install the overview application on the tablet device
- **Client Setup**: Install the user1 and user2 client applications on the two smartphones respectively
- **Map-Specific Applications**: Install separate applications for each city map (Nanjing, Suzhou, Hangzhou) on all devices
- **Network Configuration**: Ensure all devices are connected to the same Wi-Fi network
- **Display Connection**: Connect the tablet to the large display via HDMI cable
- **Application Launch**: Start the corresponding server application for the selected map first, then connect client devices with the matching map application
- **WebSocket Verification**: Confirm real-time communication is established between all devices


## Dependencies

- Android SDK
- Google Maps API
- WebSocket library
- Real-time communication framework
## Contact

- Qiuyao Cheng - Qiuyao.Cheng20@alumni.xjtlu.edu.cn
- Lingyun Yu - Lingyun.Yu@xjtlu.edu.cn
- Yu Liu - yu.liu02@xjtlu.edu.cn

## Acknowledgments

This work was supported by XJTLU RDF(22-01-092).
