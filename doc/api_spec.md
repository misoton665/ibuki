# ibuki API specification

## Object

#### Action

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|contributor_id   |string  |A contributor ID.
|action_type      |string  |A type of the Action (descripted by Fixed Parameter).
|tags             |string[]|Tags of the Action.
|body             |string  |A body of the Action.

#### Activity

All details of Activity are supplied by only root Action that is a head of the Activity.

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|root_action      |Action  |A root Action of the Activity.

## API

#### /createActivity

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|contributor_id   |string  |A contributor ID of a new Activity.
|tags             |string[]|Tags of a new Activity.

#### /createAction

Create a new Action to an Activity.

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|activity_id      |string  |An Actvity ID that a new Action will be send to.
|action           |Action  |A new Action.

## Fixed Parameter

#### Type of Action

|type    |value      |detail
|:-------|:----------|:----------------------
|Root    |"root"     |A root Action that a head of an Activity.
|Document|"document" |An Action that record decument, comment, and so on (the most usual Action).
|Comment |"comment"  |An Action that mention to an other Action by expecting for an Activity contributor.
|Question|"question" |An Action that send question to an other Action by expecting for an Activity contributor.
