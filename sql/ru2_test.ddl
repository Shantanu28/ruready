
    alter table assessment_item_answers 
        drop 
        foreign key FK916ED5A6E77B64E;

    alter table assessment_item_answers 
        drop 
        foreign key FK916ED5A62D9814AA;

    alter table assessment_item_choices 
        drop 
        foreign key FKF0BF742370DB72AE;

    alter table assessment_item_choices 
        drop 
        foreign key FKF0BF74232D9814AA;

    alter table assessment_item_hints 
        drop 
        foreign key FK7933A83D8CCA5A2E;

    alter table assessment_item_hints 
        drop 
        foreign key FK7933A83D2D9814AA;

    alter table assessment_item_responses 
        drop 
        foreign key FK174A09432D9814AA;

    alter table assessment_item_responses 
        drop 
        foreign key FK174A0943C68922A1;

    alter table expectation_assessment_item 
        drop 
        foreign key FK5A3626D35ED18BD;

    alter table expectation_assessment_item 
        drop 
        foreign key FK5A3626D71C80B7F;

    alter table forum 
        drop 
        foreign key FK5D18D2128A74B43;

    alter table forum_persons 
        drop 
        foreign key FKD6E652A07E108E39;

    alter table forum_persons 
        drop 
        foreign key FKD6E652A077B475FC;

    alter table knowledge_assessment_item 
        drop 
        foreign key FKD904E8AFCDFF863F;

    alter table knowledge_assessment_item 
        drop 
        foreign key FKD904E8AFB803E801;

    alter table node 
        drop 
        foreign key FK33AE021489F22;

    alter table node 
        drop 
        foreign key FK33AE0243AB6918;

    alter table node 
        drop 
        foreign key FK33AE028E0C5D61;

    alter table node_answers 
        drop 
        foreign key FK233BEB38FE08C0AF;

    alter table node_answers 
        drop 
        foreign key FK233BEB38D31FD4BA;

    alter table node_choices 
        drop 
        foreign key FK828C89B5FE08C0AF;

    alter table node_choices 
        drop 
        foreign key FK828C89B53583911A;

    alter table node_hints 
        drop 
        foreign key FK5C0CFA4FFE08C0AF;

    alter table node_hints 
        drop 
        foreign key FK5C0CFA4F2B7F239A;

    alter table node_messages 
        drop 
        foreign key FK467D26E98F0E8109;

    alter table node_messages 
        drop 
        foreign key FK467D26E95052E11F;

    alter table node_tags 
        drop 
        foreign key FK1BC3B0368F0E8109;

    alter table node_tags 
        drop 
        foreign key FK1BC3B0368F12993;

    alter table product 
        drop 
        foreign key FKED8DCCEFAE58169D;

    alter table simple_user 
        drop 
        foreign key FKAB6EB238FB5ECD1;

    alter table student_transcript 
        drop 
        foreign key FKFAF1A83A47063D8;

    alter table student_transcript 
        drop 
        foreign key FKFAF1A83AACC5D039;

    alter table student_transcript 
        drop 
        foreign key FKFAF1A83A6254A2CC;

    alter table student_transcript_expectation_assessments 
        drop 
        foreign key FKAAEEC369CFCA12F2;

    alter table student_transcript_expectation_assessments 
        drop 
        foreign key FKAAEEC369A92A1173;

    alter table student_transcript_knowledge_assessments 
        drop 
        foreign key FKA0A33D6BCFCA12F2;

    alter table student_transcript_knowledge_assessments 
        drop 
        foreign key FKA0A33D6BD14F7637;

    alter table student_transcript_level1practices 
        drop 
        foreign key FKDA4C9E06CFCA12F2;

    alter table student_transcript_level1practices 
        drop 
        foreign key FKDA4C9E06A45A2E92;

    alter table student_transcript_level2practices 
        drop 
        foreign key FKCE960F25CFCA12F2;

    alter table student_transcript_level2practices 
        drop 
        foreign key FKCE960F2598A39FB1;

    alter table student_transcript_level3practices 
        drop 
        foreign key FKC2DF8044CFCA12F2;

    alter table student_transcript_level3practices 
        drop 
        foreign key FKC2DF80448CED10D0;

    alter table student_transcript_level4practices 
        drop 
        foreign key FKB728F163CFCA12F2;

    alter table student_transcript_level4practices 
        drop 
        foreign key FKB728F163813681EF;

    alter table studentrole_school 
        drop 
        foreign key FK4EE3E242C8F262E6;

    alter table studentrole_school 
        drop 
        foreign key FK4EE3E2423C60E41E;

    alter table teacher_school_membership 
        drop 
        foreign key FKC4D26410E815A1;

    alter table teacher_school_membership 
        drop 
        foreign key FKC4D26452E29AE5;

    alter table user 
        drop 
        foreign key FK36EBCB5F30691B;

    alter table user_group 
        drop 
        foreign key FK72A9010B52E29AE5;

    alter table user_group 
        drop 
        foreign key FK72A9010B6254A2CC;

    alter table user_group_membership 
        drop 
        foreign key FK70FA578A3A4FF77A;

    alter table user_group_membership 
        drop 
        foreign key FK70FA578A47063D8;

    alter table user_group_moderator 
        drop 
        foreign key FK6DB30ED147063D8;

    alter table user_group_moderator 
        drop 
        foreign key FK6DB30ED19C2A5F56;

    alter table user_group_subtopics 
        drop 
        foreign key FK63D513058EAEE08;

    alter table user_group_subtopics 
        drop 
        foreign key FK63D5130DF1FE4A5;

    alter table user_roles 
        drop 
        foreign key FK73429949958CDCB1;

    alter table user_session 
        drop 
        foreign key FKD1401A229E88DA6D;

    alter table user_user_sessions 
        drop 
        foreign key FK3D4DE91DE156EBD2;

    alter table user_user_sessions 
        drop 
        foreign key FK3D4DE91D9E88DA6D;

    drop table if exists abstract_message;

    drop table if exists abstract_phone;

    drop table if exists answer;

    drop table if exists assessment;

    drop table if exists assessment_item;

    drop table if exists assessment_item_answer;

    drop table if exists assessment_item_answers;

    drop table if exists assessment_item_choice;

    drop table if exists assessment_item_choices;

    drop table if exists assessment_item_hint;

    drop table if exists assessment_item_hints;

    drop table if exists assessment_item_response;

    drop table if exists assessment_item_responses;

    drop table if exists audit_message;

    drop table if exists base;

    drop table if exists bean;

    drop table if exists choice;

    drop table if exists expectation_assessment_item;

    drop table if exists forum;

    drop table if exists forum_persons;

    drop table if exists global_property;

    drop table if exists hint;

    drop table if exists hit_message;

    drop table if exists knowledge_assessment_item;

    drop table if exists node;

    drop table if exists node_answers;

    drop table if exists node_choices;

    drop table if exists node_hints;

    drop table if exists node_messages;

    drop table if exists node_tags;

    drop table if exists person;

    drop table if exists product;

    drop table if exists question_state;

    drop table if exists simple_item;

    drop table if exists simple_user;

    drop table if exists student_transcript;

    drop table if exists student_transcript_expectation_assessments;

    drop table if exists student_transcript_knowledge_assessments;

    drop table if exists student_transcript_level1practices;

    drop table if exists student_transcript_level2practices;

    drop table if exists student_transcript_level3practices;

    drop table if exists student_transcript_level4practices;

    drop table if exists studentrole_school;

    drop table if exists supplier;

    drop table if exists teacher_school_membership;

    drop table if exists user;

    drop table if exists user_group;

    drop table if exists user_group_membership;

    drop table if exists user_group_moderator;

    drop table if exists user_group_subtopics;

    drop table if exists user_roles;

    drop table if exists user_session;

    drop table if exists user_user_sessions;

    create table abstract_message (
        dtype varchar(31) not null,
        id bigint not null auto_increment,
        comment varchar(80),
        date bigint not null,
        primary key (id)
    );

    create table abstract_phone (
        type varchar(31) not null,
        id bigint not null auto_increment,
        number integer not null,
        locale varchar(255),
        primary key (id)
    );

    create table answer (
        id bigint not null auto_increment,
        answer_text varchar(255),
        primary key (id)
    );

    create table assessment (
        assessment_type varchar(15) not null,
        assessment_id bigint not null auto_increment,
        last_updated datetime not null,
        score numeric(4,3),
        status varchar(255) not null,
        primary key (assessment_id)
    );

    create table assessment_item (
        item_type varchar(15) not null,
        id bigint not null auto_increment,
        formulation text,
        name varchar(50) not null,
        reference_id bigint,
        score numeric(4,3),
        status varchar(255) not null,
        end_date bigint,
        start_date bigint,
        expectation_baseline numeric(4,3),
        negative bit,
        expectation_value numeric(4,3),
        level integer,
        number_of_choices integer,
        parameters varchar(150),
        question_format varchar(255),
        question_precision integer,
        question_type varchar(255),
        variables varchar(150),
        primary key (id)
    );

    create table assessment_item_answer (
        id bigint not null auto_increment,
        answer_text varchar(255),
        primary key (id)
    );

    create table assessment_item_answers (
        assessment_item bigint not null,
        answers bigint not null,
        index_answer integer not null,
        primary key (assessment_item, index_answer),
        unique (answers)
    );

    create table assessment_item_choice (
        id bigint not null auto_increment,
        choice_text varchar(150),
        correct bit,
        primary key (id)
    );

    create table assessment_item_choices (
        assessment_item bigint not null,
        choices bigint not null,
        index_choice integer not null,
        primary key (assessment_item, index_choice),
        unique (choices)
    );

    create table assessment_item_hint (
        id bigint not null auto_increment,
        hint1text varchar(255),
        hint2text varchar(255),
        keyword1text varchar(255),
        keyword2text varchar(255),
        primary key (id)
    );

    create table assessment_item_hints (
        assessment_item bigint not null,
        hints bigint not null,
        index_hint integer not null,
        primary key (assessment_item, index_hint),
        unique (hints)
    );

    create table assessment_item_response (
        id bigint not null auto_increment,
        answer text,
        response_type varchar(255),
        score numeric(4,3),
        primary key (id)
    );

    create table assessment_item_responses (
        assessment_item bigint not null,
        responses bigint not null,
        index_responses integer not null,
        primary key (assessment_item, index_responses),
        unique (responses)
    );

    create table audit_message (
        id bigint not null auto_increment,
        comment varchar(80),
        date bigint not null,
        action varchar(255),
        email varchar(255),
        first_name varchar(40),
        last_name varchar(40),
        middle_initial varchar(1),
        version integer,
        primary key (id)
    );

    create table base (
        dtype varchar(31) not null,
        id bigint not null auto_increment,
        base_name varchar(200),
        child_name varchar(255),
        primary key (id)
    );

    create table bean (
        id bigint not null auto_increment,
        field1 varchar(255),
        primary key (id)
    );

    create table choice (
        id bigint not null auto_increment,
        choice_text varchar(150),
        correct bit,
        primary key (id)
    );

    create table expectation_assessment_item (
        assessment_id bigint not null,
        item_id bigint not null,
        item_index integer not null,
        primary key (assessment_id, item_index),
        unique (item_id)
    );

    create table forum (
        id bigint not null auto_increment,
        name varchar(80),
        moderator bigint,
        primary key (id)
    );

    create table forum_persons (
        forum bigint not null,
        persons bigint not null
    );

    create table global_property (
        dtype varchar(31) not null,
        id bigint not null auto_increment,
        name varchar(30) unique,
        value bigint,
        primary key (id)
    );

    create table hint (
        id bigint not null auto_increment,
        hint1text varchar(255),
        hint2text varchar(255),
        keyword1text varchar(255),
        keyword2text varchar(255),
        primary key (id)
    );

    create table hit_message (
        id bigint not null auto_increment,
        comment varchar(80),
        date bigint not null,
        user_agent varchar(255),
        primary key (id)
    );

    create table knowledge_assessment_item (
        assessment_id bigint not null,
        item_id bigint not null,
        assessment_index integer not null,
        primary key (assessment_id, assessment_index),
        unique (item_id)
    );

    create table node (
        item_type varchar(31) not null,
        id bigint not null auto_increment,
        comment varchar(100),
        comparator_type varchar(255),
        depth integer,
        name varchar(50),
        serial_no integer,
        version integer,
        read_only bit,
        unique_name varchar(30) unique,
        abbreviation varchar(20),
        syllabus_url varchar(255),
        univ_catalog_number varchar(20),
        formulation text,
        level integer,
        number_of_choices integer,
        parameters varchar(150),
        question_precision integer,
        question_type varchar(255),
        variables varchar(150),
        negative bit,
        county_string varchar(255),
        phone_code integer,
        county varchar(255),
        address1 varchar(255),
        address2 varchar(255),
        city varchar(255),
        district varchar(255),
        fax varchar(255),
        institution_type varchar(255),
        phone1 varchar(255),
        phone2 varchar(255),
        sector varchar(255),
        url varchar(255),
        zip_code varchar(255),
        content varchar(255),
        state bigint,
        parent_id bigint,
        latest_message bigint,
        primary key (id)
    );

    create table node_answers (
        node bigint not null,
        answers bigint not null,
        index_answer integer not null,
        primary key (node, index_answer),
        unique (answers)
    );

    create table node_choices (
        node bigint not null,
        choices bigint not null,
        index_choice integer not null,
        primary key (node, index_choice),
        unique (choices)
    );

    create table node_hints (
        node bigint not null,
        hints bigint not null,
        index_hint integer not null,
        primary key (node, index_hint),
        unique (hints)
    );

    create table node_messages (
        node bigint not null,
        messages bigint not null,
        index_message integer not null,
        primary key (node, index_message),
        unique (messages)
    );

    create table node_tags (
        node bigint not null,
        tags bigint not null,
        primary key (node, tags)
    );

    create table person (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table product (
        id bigint not null auto_increment,
        description varchar(255),
        name varchar(255),
        price double precision not null,
        supplier_id bigint,
        primary key (id)
    );

    create table question_state (
        id bigint not null auto_increment,
        last_modified datetime,
        stateid varchar(255),
        primary key (id)
    );

    create table simple_item (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table simple_user (
        id bigint not null auto_increment,
        name varchar(255),
        phone bigint,
        primary key (id)
    );

    create table student_transcript (
        transcript_type varchar(10) not null,
        id bigint not null auto_increment,
        active_status varchar(255) not null,
        created datetime not null,
        progress_status varchar(255) not null,
        recommended_level integer not null,
        course_id bigint,
        student_id bigint,
        group_id bigint,
        primary key (id)
    );

    create table student_transcript_expectation_assessments (
        student_transcript bigint not null,
        expectation_assessments bigint not null,
        assessment_index integer not null,
        primary key (student_transcript, assessment_index),
        unique (expectation_assessments)
    );

    create table student_transcript_knowledge_assessments (
        student_transcript bigint not null,
        knowledge_assessments bigint not null,
        assessment_index integer not null,
        primary key (student_transcript, assessment_index),
        unique (knowledge_assessments)
    );

    create table student_transcript_level1practices (
        student_transcript bigint not null,
        level1practices bigint not null,
        assessment_index integer not null,
        primary key (student_transcript, assessment_index),
        unique (level1practices)
    );

    create table student_transcript_level2practices (
        student_transcript bigint not null,
        level2practices bigint not null,
        assessment_index integer not null,
        primary key (student_transcript, assessment_index),
        unique (level2practices)
    );

    create table student_transcript_level3practices (
        student_transcript bigint not null,
        level3practices bigint not null,
        assessment_index integer not null,
        primary key (student_transcript, assessment_index),
        unique (level3practices)
    );

    create table student_transcript_level4practices (
        student_transcript bigint not null,
        level4practices bigint not null,
        assessment_index integer not null,
        primary key (student_transcript, assessment_index),
        unique (level4practices)
    );

    create table studentrole_school (
        role_id bigint not null,
        item_id bigint not null,
        primary key (role_id, item_id)
    );

    create table supplier (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table teacher_school_membership (
        member_id bigint not null,
        school_id bigint not null,
        status varchar(255) not null,
        status_date datetime not null,
        status_reason varchar(255),
        primary key (member_id, school_id)
    );

    create table user (
        id bigint not null auto_increment,
        age_group varchar(255),
        created_date bigint,
        default_perspective varchar(255),
        email varchar(255) unique,
        ethnicity varchar(255),
        gender varchar(255),
        highest_role varchar(255),
        is_loggedin bit not null,
        language varchar(255),
        last_logged_in_date bigint,
        first_name varchar(40),
        last_name varchar(40),
        middle_initial varchar(1),
        password varchar(30),
        student_identifier varchar(40),
        user_status varchar(255),
        obj_version integer,
        latest_user_session bigint,
        primary key (id)
    );

    create table user_group (
        id bigint not null auto_increment,
        status varchar(255) not null,
        created datetime not null,
        name varchar(80),
        obj_version integer,
        school_id bigint,
        course_id bigint,
        primary key (id),
        unique (name, course_id, school_id)
    );

    create table user_group_membership (
        group_id bigint not null,
        member_id bigint not null,
        status varchar(255) not null,
        status_date datetime not null,
        primary key (group_id, member_id)
    );

    create table user_group_moderator (
        group_id bigint not null,
        moderator_id bigint not null,
        created datetime not null,
        owner bit not null,
        primary key (group_id, moderator_id)
    );

    create table user_group_subtopics (
        user_group bigint not null,
        subtopics bigint not null,
        primary key (user_group, subtopics),
        unique (subtopics)
    );

    create table user_roles (
        role_type varchar(15) not null,
        role_id bigint not null auto_increment,
        created datetime not null,
        obj_version integer,
        user_id bigint,
        primary key (role_id)
    );

    create table user_session (
        id bigint not null auto_increment,
        screen_height integer,
        screen_width integer,
        end_date bigint,
        start_date bigint,
        user bigint,
        primary key (id)
    );

    create table user_user_sessions (
        user bigint not null,
        user_sessions bigint not null,
        index_user_session integer not null,
        primary key (user, index_user_session),
        unique (user_sessions)
    );

    alter table assessment_item_answers 
        add index FK916ED5A6E77B64E (answers), 
        add constraint FK916ED5A6E77B64E 
        foreign key (answers) 
        references assessment_item_answer (id);

    alter table assessment_item_answers 
        add index FK916ED5A62D9814AA (assessment_item), 
        add constraint FK916ED5A62D9814AA 
        foreign key (assessment_item) 
        references assessment_item (id);

    alter table assessment_item_choices 
        add index FKF0BF742370DB72AE (choices), 
        add constraint FKF0BF742370DB72AE 
        foreign key (choices) 
        references assessment_item_choice (id);

    alter table assessment_item_choices 
        add index FKF0BF74232D9814AA (assessment_item), 
        add constraint FKF0BF74232D9814AA 
        foreign key (assessment_item) 
        references assessment_item (id);

    alter table assessment_item_hints 
        add index FK7933A83D8CCA5A2E (hints), 
        add constraint FK7933A83D8CCA5A2E 
        foreign key (hints) 
        references assessment_item_hint (id);

    alter table assessment_item_hints 
        add index FK7933A83D2D9814AA (assessment_item), 
        add constraint FK7933A83D2D9814AA 
        foreign key (assessment_item) 
        references assessment_item (id);

    alter table assessment_item_responses 
        add index FK174A09432D9814AA (assessment_item), 
        add constraint FK174A09432D9814AA 
        foreign key (assessment_item) 
        references assessment_item (id);

    alter table assessment_item_responses 
        add index FK174A0943C68922A1 (responses), 
        add constraint FK174A0943C68922A1 
        foreign key (responses) 
        references assessment_item_response (id);

    alter table expectation_assessment_item 
        add index FK5A3626D35ED18BD (assessment_id), 
        add constraint FK5A3626D35ED18BD 
        foreign key (assessment_id) 
        references assessment (assessment_id);

    alter table expectation_assessment_item 
        add index FK5A3626D71C80B7F (item_id), 
        add constraint FK5A3626D71C80B7F 
        foreign key (item_id) 
        references assessment_item (id);

    alter table forum 
        add index FK5D18D2128A74B43 (moderator), 
        add constraint FK5D18D2128A74B43 
        foreign key (moderator) 
        references person (id);

    alter table forum_persons 
        add index FKD6E652A07E108E39 (forum), 
        add constraint FKD6E652A07E108E39 
        foreign key (forum) 
        references forum (id);

    alter table forum_persons 
        add index FKD6E652A077B475FC (persons), 
        add constraint FKD6E652A077B475FC 
        foreign key (persons) 
        references person (id);

    alter table knowledge_assessment_item 
        add index FKD904E8AFCDFF863F (assessment_id), 
        add constraint FKD904E8AFCDFF863F 
        foreign key (assessment_id) 
        references assessment (assessment_id);

    alter table knowledge_assessment_item 
        add index FKD904E8AFB803E801 (item_id), 
        add constraint FKD904E8AFB803E801 
        foreign key (item_id) 
        references assessment_item (id);

    alter table node 
        add index FK33AE021489F22 (latest_message), 
        add constraint FK33AE021489F22 
        foreign key (latest_message) 
        references audit_message (id);

    alter table node 
        add index FK33AE0243AB6918 (state), 
        add constraint FK33AE0243AB6918 
        foreign key (state) 
        references question_state (id);

    alter table node 
        add index FK33AE028E0C5D61 (parent_id), 
        add constraint FK33AE028E0C5D61 
        foreign key (parent_id) 
        references node (id);

    alter table node_answers 
        add index FK233BEB38FE08C0AF (node), 
        add constraint FK233BEB38FE08C0AF 
        foreign key (node) 
        references node (id);

    alter table node_answers 
        add index FK233BEB38D31FD4BA (answers), 
        add constraint FK233BEB38D31FD4BA 
        foreign key (answers) 
        references answer (id);

    alter table node_choices 
        add index FK828C89B5FE08C0AF (node), 
        add constraint FK828C89B5FE08C0AF 
        foreign key (node) 
        references node (id);

    alter table node_choices 
        add index FK828C89B53583911A (choices), 
        add constraint FK828C89B53583911A 
        foreign key (choices) 
        references choice (id);

    alter table node_hints 
        add index FK5C0CFA4FFE08C0AF (node), 
        add constraint FK5C0CFA4FFE08C0AF 
        foreign key (node) 
        references node (id);

    alter table node_hints 
        add index FK5C0CFA4F2B7F239A (hints), 
        add constraint FK5C0CFA4F2B7F239A 
        foreign key (hints) 
        references hint (id);

    alter table node_messages 
        add index FK467D26E98F0E8109 (node), 
        add constraint FK467D26E98F0E8109 
        foreign key (node) 
        references node (id);

    alter table node_messages 
        add index FK467D26E95052E11F (messages), 
        add constraint FK467D26E95052E11F 
        foreign key (messages) 
        references audit_message (id);

    alter table node_tags 
        add index FK1BC3B0368F0E8109 (node), 
        add constraint FK1BC3B0368F0E8109 
        foreign key (node) 
        references node (id);

    alter table node_tags 
        add index FK1BC3B0368F12993 (tags), 
        add constraint FK1BC3B0368F12993 
        foreign key (tags) 
        references node (id);

    alter table product 
        add index FKED8DCCEFAE58169D (supplier_id), 
        add constraint FKED8DCCEFAE58169D 
        foreign key (supplier_id) 
        references supplier (id);

    alter table simple_user 
        add index FKAB6EB238FB5ECD1 (phone), 
        add constraint FKAB6EB238FB5ECD1 
        foreign key (phone) 
        references abstract_phone (id);

    alter table student_transcript 
        add index FKFAF1A83A47063D8 (group_id), 
        add constraint FKFAF1A83A47063D8 
        foreign key (group_id) 
        references user_group (id);

    alter table student_transcript 
        add index FKFAF1A83AACC5D039 (student_id), 
        add constraint FKFAF1A83AACC5D039 
        foreign key (student_id) 
        references user_roles (role_id);

    alter table student_transcript 
        add index FKFAF1A83A6254A2CC (course_id), 
        add constraint FKFAF1A83A6254A2CC 
        foreign key (course_id) 
        references node (id);

    alter table student_transcript_expectation_assessments 
        add index FKAAEEC369CFCA12F2 (student_transcript), 
        add constraint FKAAEEC369CFCA12F2 
        foreign key (student_transcript) 
        references student_transcript (id);

    alter table student_transcript_expectation_assessments 
        add index FKAAEEC369A92A1173 (expectation_assessments), 
        add constraint FKAAEEC369A92A1173 
        foreign key (expectation_assessments) 
        references assessment (assessment_id);

    alter table student_transcript_knowledge_assessments 
        add index FKA0A33D6BCFCA12F2 (student_transcript), 
        add constraint FKA0A33D6BCFCA12F2 
        foreign key (student_transcript) 
        references student_transcript (id);

    alter table student_transcript_knowledge_assessments 
        add index FKA0A33D6BD14F7637 (knowledge_assessments), 
        add constraint FKA0A33D6BD14F7637 
        foreign key (knowledge_assessments) 
        references assessment (assessment_id);

    alter table student_transcript_level1practices 
        add index FKDA4C9E06CFCA12F2 (student_transcript), 
        add constraint FKDA4C9E06CFCA12F2 
        foreign key (student_transcript) 
        references student_transcript (id);

    alter table student_transcript_level1practices 
        add index FKDA4C9E06A45A2E92 (level1practices), 
        add constraint FKDA4C9E06A45A2E92 
        foreign key (level1practices) 
        references assessment (assessment_id);

    alter table student_transcript_level2practices 
        add index FKCE960F25CFCA12F2 (student_transcript), 
        add constraint FKCE960F25CFCA12F2 
        foreign key (student_transcript) 
        references student_transcript (id);

    alter table student_transcript_level2practices 
        add index FKCE960F2598A39FB1 (level2practices), 
        add constraint FKCE960F2598A39FB1 
        foreign key (level2practices) 
        references assessment (assessment_id);

    alter table student_transcript_level3practices 
        add index FKC2DF8044CFCA12F2 (student_transcript), 
        add constraint FKC2DF8044CFCA12F2 
        foreign key (student_transcript) 
        references student_transcript (id);

    alter table student_transcript_level3practices 
        add index FKC2DF80448CED10D0 (level3practices), 
        add constraint FKC2DF80448CED10D0 
        foreign key (level3practices) 
        references assessment (assessment_id);

    alter table student_transcript_level4practices 
        add index FKB728F163CFCA12F2 (student_transcript), 
        add constraint FKB728F163CFCA12F2 
        foreign key (student_transcript) 
        references student_transcript (id);

    alter table student_transcript_level4practices 
        add index FKB728F163813681EF (level4practices), 
        add constraint FKB728F163813681EF 
        foreign key (level4practices) 
        references assessment (assessment_id);

    alter table studentrole_school 
        add index FK4EE3E242C8F262E6 (item_id), 
        add constraint FK4EE3E242C8F262E6 
        foreign key (item_id) 
        references node (id);

    alter table studentrole_school 
        add index FK4EE3E2423C60E41E (role_id), 
        add constraint FK4EE3E2423C60E41E 
        foreign key (role_id) 
        references user_roles (role_id);

    alter table teacher_school_membership 
        add index FKC4D26410E815A1 (member_id), 
        add constraint FKC4D26410E815A1 
        foreign key (member_id) 
        references user_roles (role_id);

    alter table teacher_school_membership 
        add index FKC4D26452E29AE5 (school_id), 
        add constraint FKC4D26452E29AE5 
        foreign key (school_id) 
        references node (id);

    alter table user 
        add index FK36EBCB5F30691B (latest_user_session), 
        add constraint FK36EBCB5F30691B 
        foreign key (latest_user_session) 
        references user_session (id);

    alter table user_group 
        add index FK72A9010B52E29AE5 (school_id), 
        add constraint FK72A9010B52E29AE5 
        foreign key (school_id) 
        references node (id);

    alter table user_group 
        add index FK72A9010B6254A2CC (course_id), 
        add constraint FK72A9010B6254A2CC 
        foreign key (course_id) 
        references node (id);

    alter table user_group_membership 
        add index FK70FA578A3A4FF77A (member_id), 
        add constraint FK70FA578A3A4FF77A 
        foreign key (member_id) 
        references user_roles (role_id);

    alter table user_group_membership 
        add index FK70FA578A47063D8 (group_id), 
        add constraint FK70FA578A47063D8 
        foreign key (group_id) 
        references user_group (id);

    alter table user_group_moderator 
        add index FK6DB30ED147063D8 (group_id), 
        add constraint FK6DB30ED147063D8 
        foreign key (group_id) 
        references user_group (id);

    alter table user_group_moderator 
        add index FK6DB30ED19C2A5F56 (moderator_id), 
        add constraint FK6DB30ED19C2A5F56 
        foreign key (moderator_id) 
        references user_roles (role_id);

    alter table user_group_subtopics 
        add index FK63D513058EAEE08 (user_group), 
        add constraint FK63D513058EAEE08 
        foreign key (user_group) 
        references user_group (id);

    alter table user_group_subtopics 
        add index FK63D5130DF1FE4A5 (subtopics), 
        add constraint FK63D5130DF1FE4A5 
        foreign key (subtopics) 
        references node (id);

    alter table user_roles 
        add index FK73429949958CDCB1 (user_id), 
        add constraint FK73429949958CDCB1 
        foreign key (user_id) 
        references user (id);

    alter table user_session 
        add index FKD1401A229E88DA6D (user), 
        add constraint FKD1401A229E88DA6D 
        foreign key (user) 
        references user (id);

    alter table user_user_sessions 
        add index FK3D4DE91DE156EBD2 (user_sessions), 
        add constraint FK3D4DE91DE156EBD2 
        foreign key (user_sessions) 
        references user_session (id);

    alter table user_user_sessions 
        add index FK3D4DE91D9E88DA6D (user), 
        add constraint FK3D4DE91D9E88DA6D 
        foreign key (user) 
        references user (id);
