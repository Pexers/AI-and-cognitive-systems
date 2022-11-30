# AI-and-cognitive-systems
This repository contains a few projects that solve generic challenges of artificial intelligence and cognitive systems using Java. Every module contains some gradle tests that validate the respective solution.

## Module 1 - Neural Networks
Learning the XOR logic function using the _forward propagation algorithm_ for neural networks.

<p align="center">
<img src="https://user-images.githubusercontent.com/47757441/185814110-f2196dcf-204d-4a80-87d8-3301780ec895.png" width="340">
</p>

## Module 2 - Optimization, Genetic Algorithm and Basic Planning
The challenges used for the opmitimization and genetic algorithms were inspired by google AI challenges:
https://developers.google.com/optimization/routing

Below, it's illustrated one of the choosen problems, where the goal is to assign every worker to a single task while trying to minimize the total cost as much as possible. Two workers can't be performing the same task.

<p align="center">
<img src="https://user-images.githubusercontent.com/47757441/185814121-7e1ca33e-c6cf-4eb9-9746-dbb663316a5b.png" width="400">
<img src="https://user-images.githubusercontent.com/47757441/185814127-0b7315b2-b5ee-43b7-86ee-b52a9e9e5f45.png" width="380">
</p>

## Module 3 - Autonomous Agent using Reactive, Adaptive and Deliberative layers
The purpose of this module is to create an agent capable of navigating in a space of obstacles and a target to collect. The agent movement is continuous or discrete, depending on the challenge. The first image shows a reactive agent (continuous movement), while the second image shows an adaptive agent (discrete movement). The deliberative layer uses both the adaptive and reactive layer. The results obtained for the deliberative implementation weren't as good as the other two layers.

<p align="center">
<img src="https://user-images.githubusercontent.com/47757441/185814134-a40d2fa4-2bca-4d5a-b4bd-0a6a217b27fa.png" width="330">
<img src="https://user-images.githubusercontent.com/47757441/185814139-775105b9-34aa-4e32-8590-1a997bc7cab2.png" width="330">
</p>
