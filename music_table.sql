-- 音乐表
CREATE TABLE IF NOT EXISTS music (
    music_id VARCHAR(32) PRIMARY KEY,
    music_name VARCHAR(100) NOT NULL COMMENT '音乐名称',
    music_url VARCHAR(500) NOT NULL COMMENT '音乐URL（OSS地址）',
    music_artist VARCHAR(100) DEFAULT '' COMMENT '艺术家/歌手',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='音乐表';
