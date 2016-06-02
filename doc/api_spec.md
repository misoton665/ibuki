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
An activity ID is its root action ID.

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|root_action      |Action  |A root Action of the Activity.

#### User

User also be called with "contributor" or "member".

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|user_id          |string  |An user ID that the user has.
|user_name        |string  |An user name that the user has.

#### Group

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|group_id         |string  |A group ID that the group has.
|group_name       |string  |A group name that the group has.
|owner_id         |string  |A user ID of the group owner.
|member_ids       |string[]|All user IDs of members that have been joined to the group.
|activity_ids     |string[]|All IDs of activities that have been created from a member of the group. 

#### Error Status

If you requested with invalid API or invalid parameteres, we will return the error status object (JSON).

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|error_id         |integer |A error status ID.
|error_message    |string  |A description of error details.

## API

#### POST /create/activity

Request parameters

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|contributor_id   |string  |A contributor ID of a new Activity.
|tags             |string[]|Tags of a new Activity.

Result JSON

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|activity         |Activity|A created Activity.

#### POST /create/action

Create a new Action to an Activity.

Request parameters

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|activity_id      |string  |An Actvity ID that a new Action will be send to.
|action           |Action  |A new Action.

Result JSON

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|action           |Action  |A created Action.

#### GET /search/activity

Request parameters

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|actibity_id      |string  |
|contributor_id   |string  |
|tags             |string[]|

Result JSON

|parameter        |type      |detail
|:----------------|:---------|:-------------------------------
|activities       |Activity[]|

#### GET /search/action

Request parameters

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|actibity_id      |string  |
|action_id        |string  |
|contributor_id   |string  |
|tags             |string[]|
|root_only        |boolean |

Result JSON

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|activities       |Action[]|

#### GET /search/user

Request parameters

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|user_id          |string  |

Result JSON

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|user             |User    |

#### GET /search/group

Request parameters

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|group_id         |string  |

Result JSON

|parameter        |type    |detail
|:----------------|:-------|:-------------------------------
|group            |Group   |

## Fixed Parameter

#### Type of Action

|type    |value      |detail
|:-------|:----------|:----------------------
|Root    |"root"     |A root Action that a head of an Activity.
|Document|"document" |An Action that record decument, comment, and so on (the most usual Action).
|Comment |"comment"  |An Action that mention to an other Action by expecting for an Activity contributor.
|Question|"question" |An Action that send question to an other Action by expecting for an Activity contributor.
