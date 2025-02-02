package com.github.crashdemons.playerheads;

import com.github.crashdemons.playerheads.compatibility.CompatibleSkullMaterial;

import java.net.URL;
import java.util.UUID;
import org.shininet.bukkit.playerheads.Lang;
import java.util.HashMap;
import org.shininet.bukkit.playerheads.Formatter;
import com.github.crashdemons.playerheads.api.HeadType;
import com.github.crashdemons.playerheads.compatibility.SkullDetails;

/**
 * Enumeration of skulls with associated UUID (randomly assigned) and texture
 * string.
 * <p>
 * This enum should match correspondingly named entries in bukkit EntityType
 * enum.
 * <p>
 * Inspired by shininet-CustomSkullType, presumably by meiskam
 *
 * @author crashdemons
 * @author MagmaVoid_
 */
public enum TexturedSkullType implements HeadType {

    //Entity skull settings - big thanks to MagmaVoid_ for finding all of these textures.
    /**
     * Skull Type used for indicating unknown playerheads.
     */
    PLAYER(//used for unknown player heads
            CompatibleSkullMaterial.PLAYER,
            "a1ae4481-f3f0-4af9-a83e-75d3a7f87853",
            "http://textures.minecraft.net/texture/eb7af9e4411217c7de9c60acbd3c3fd6519783332a1b3bc56fbfce90721ef35"
    ),
    CREAKING(
            "7915b2aa-0e39-4e05-aca0-73c5be0aa370",
            "http://textures.minecraft.net/texture/a575bac234cf86b124d3cc870bd6b737d27679673a616faf2e996f9949c6153f"
    ),
    BREEZE(
            "2c9b79fb-adc3-4c6c-a59b-25d7c559e73d",
            "http://textures.minecraft.net/texture/cd6e602f76f80c0657b5aed64e267eeea702b31e6dae86346c8506f2535ced02"
    ),
    ARMADILLO(
            "4be5ae47-6395-46ab-8f48-be800a83cfc6",
            "http://textures.minecraft.net/texture/c9c1e96ce985725e22ed6ccf0f4c4810c729a2538b97bda06faeb3b92799c878"
    ),
    BOGGED(
            "038b44c3-a528-4bac-a9af-14f3f77e1cf7",
            "http://textures.minecraft.net/texture/a3b9003ba2d05562c75119b8a62185c67130e9282f7acbac4bc2824c21eb95d9"
    ),
    CAMEL(
            "23393c75-845c-4043-a8f3-7422e2f0de8b",
            "http://textures.minecraft.net/texture/a1091ce6e559c26be77ee60456b62122990f9519f514b11df3564149c2729d05"
    ),
    SNIFFER(
            "a62feef4-5db5-4bc2-a27d-c70403e0caea",
            "http://textures.minecraft.net/texture/43f3be09a7353eeae94d88320cb5b242de2f719c0e5c16a486327c605db1d463"
    ),
    ALLAY(
            "b9995cf6-f729-4c8a-8975-d4ec4da86973",
            "http://textures.minecraft.net/texture/40e1c7064af7dee68677efaa95f6e6e01430b006dd91638ea2a61849254488ec"
    ),
    FROG(
            "cd7dde80-9422-4945-85c0-4d69a7af4976",
            "http://textures.minecraft.net/texture/c283412169d1dbd15032c11c5a225cd11be3875d612dad818d1d6fccfcb9bfa"
    ),
    TADPOLE(
            "9121441a-6bd4-40a3-973e-721bf78518c0",
            "http://textures.minecraft.net/texture/c52a4d788a29542863ac1cb828d11211f6d123c6ec046098a65381e2c37c37e0"
    ),
    WARDEN(
            "5463f08a-7a0e-4bb4-bd3f-05133effa66f",
            "http://textures.minecraft.net/texture/75b26ca8d2538f7f6df08c81f0adb83058016fb4193b9aa53fd04b412000afd2"
    ),
    AXOLOTL(
            "fa074329-ea4a-4535-acbd-4f0f490448f7",
            "http://textures.minecraft.net/texture/e8a8a2d7ccf0c3746e23ab5491070e0923f05b235f9a2f5d53d384353853bddc"
    ),
    GLOW_SQUID(
            "7ed5a68c-c3ec-4ddb-9355-cee7ab526570",
            "http://textures.minecraft.net/texture/2ecd0b5eb6b384db076d8446065202959dddff0161e0d723b3df0cc586d16bbd"
    ),
    GOAT(
            "5485c31d-e250-4764-a761-3e2c8b9745c4",
            "http://textures.minecraft.net/texture/957607099d06b7a8b1327093cd0a488be7c9f50b6121b22151271b59170f3c21"
    ),
    PIGLIN_BRUTE(
            "11eece7d-c455-499b-afa0-7b2972ac149d",
            "http://textures.minecraft.net/texture/3e300e9027349c4907497438bac29e3a4c87a848c50b34c21242727b57f4e1cf"
    ),
    ZOGLIN(
            "975fca56-a595-4d33-9253-2f1f3fcaaba5",
            "http://textures.minecraft.net/texture/e67e18602e03035ad68967ce090235d8996663fb9ea47578d3a7ebbc42a5ccf9"
    ),
    STRIDER(
            "d67320fb-f662-4850-89b4-b4410a66740e",
            "http://textures.minecraft.net/texture/18a9adf780ec7dd4625c9c0779052e6a15a451866623511e4c82e9655714b3c1"
    ),
    PIGLIN(
            CompatibleSkullMaterial.PIGLIN,
            "d5487658-3583-4a82-a5cc-3a4e120bf965",
            "http://textures.minecraft.net/texture/9f18107d275f1cb3a9f973e5928d5879fa40328ff3258054db6dd3e7c0ca6330"
    ),
    HOGLIN(
            "5b7ba90e-00c1-46c8-9d96-29172b656ebf",
            "http://textures.minecraft.net/texture/9bb9bc0f01dbd762a08d9e77c08069ed7c95364aa30ca1072208561b730e8d75"
    ),
    BEE(
            "e6016d4c-351c-49da-9da9-38da66a668f7",
            "http://textures.minecraft.net/texture/59ac16f296b461d05ea0785d477033e527358b4f30c266aa02f020157ffca736"
    ),
    WANDERING_TRADER(
            "70aaec20-d989-4e06-857d-285ad2dca337",
            "http://textures.minecraft.net/texture/5f1379a82290d7abe1efaabbc70710ff2ec02dd34ade386bc00c930c461cf932"
    ),
    TRADER_LLAMA(
            "47eb2118-c918-436a-a94a-8affb0a81426",
            "http://textures.minecraft.net/texture/e89a2eb17705fe7154ab041e5c76a08d41546a31ba20ea3060e3ec8edc10412c"
    ),
    FOX(
            "6c6deea6-7485-4422-a527-03c58628150b",
            "http://textures.minecraft.net/texture/16db7d507389a14bbec39de6922165b32d43657bcb6aaf4b5182825b22b4"
    ),
    CAT(
            "7f0a98d3-dc52-46c7-ac4f-cdf71a38c32e",
            "http://textures.minecraft.net/texture/45f47cd820b1974da66cdf90beb79820eca9f4cbe021b1783a30b75025d1"
    ),
    PILLAGER(
            "c64b8af5-b547-4a15-abf9-12d3eb052f37",
            "http://textures.minecraft.net/texture/6deaec344ab095b48cead7527f7dee61b063ff791f76a8fa76642c8676e2173"
    ),
    RAVAGER(
            "3a9cb258-fb4e-47e5-811f-a00e8f0cd6fc",
            "http://textures.minecraft.net/texture/3b62501cd1b87b37f628018210ec5400cb65a4d1aab74e6a3f7f62aa85db97ee"
    ),
    PANDA(
            "b59c247f-30fc-418b-a123-44f7018ab492",
            "http://textures.minecraft.net/texture/dca096eea506301bea6d4b17ee1605625a6f5082c71f74a639cc940439f47166"
    ),
    ELDER_GUARDIAN(
            "fe2a7a7e-d140-4996-9922-e1fb124fb9f7",
            "http://textures.minecraft.net/texture/1c797482a14bfcb877257cb2cff1b6e6a8b8413336ffb4c29a6139278b436b"
    ),
    WITHER_SKELETON(
            "c5b5a5a5-8a1d-4c0b-8e9f-5dd4ac8ab9d6",
            "http://textures.minecraft.net/texture/7953b6c68448e7e6b6bf8fb273d7203acd8e1be19e81481ead51f45de59a8"
    ),
    STRAY(
            "d051ef24-d218-44bf-95e1-fb30174237f1",
            "http://textures.minecraft.net/texture/78ddf76e555dd5c4aa8a0a5fc584520cd63d489c253de969f7f22f85a9a2d56"
    ),
    HUSK(
            "9bfd4d16-7232-49d3-bfd8-c965e57ba899",
            "http://textures.minecraft.net/texture/d674c63c8db5f4ca628d69a3b1f8a36e29d8fd775e1a6bdb6cabb4be4db121"
    ),
    ZOMBIE_VILLAGER(
            "8de04954-9398-4e7c-b2c3-a0684b5b5929",
            "http://textures.minecraft.net/texture/e5e08a8776c1764c3fe6a6ddd412dfcb87f41331dad479ac96c21df4bf3ac89c"
    ),
    SKELETON_HORSE(
            "3d84a760-800d-4f87-a537-18446aad8623",
            "http://textures.minecraft.net/texture/47effce35132c86ff72bcae77dfbb1d22587e94df3cbc2570ed17cf8973a"
    ),
    ZOMBIE_HORSE(
            "4bde7ee9-e719-4e2a-9249-26a3b8ac765e",
            "http://textures.minecraft.net/texture/d22950f2d3efddb18de86f8f55ac518dce73f12a6e0f8636d551d8eb480ceec"
    ),
    DONKEY(
            "96bc6d6d-3599-49fa-b044-353f288da370",
            "http://textures.minecraft.net/texture/63a976c047f412ebc5cb197131ebef30c004c0faf49d8dd4105fca1207edaff3"
    ),
    MULE(
            "59c3af02-e6d8-481c-b0e3-46d09044b17a",
            "http://textures.minecraft.net/texture/a0486a742e7dda0bae61ce2f55fa13527f1c3b334c57c034bb4cf132fb5f5f"
    ),
    EVOKER(
            "3b8e7675-6d30-45b9-8a39-bde80cff58ff",
            "http://textures.minecraft.net/texture/3433322e2ccbd9c55ef41d96f38dbc666c803045b24391ac9391dccad7cd"
    ),
    VEX(
            "7379ac5e-bdec-4792-8e04-4967274da5db",
            "http://textures.minecraft.net/texture/c2ec5a516617ff1573cd2f9d5f3969f56d5575c4ff4efefabd2a18dc7ab98cd"
    ),
    VINDICATOR(
            "c28e1263-45f1-4e19-8af4-01015507fc94",
            "http://textures.minecraft.net/texture/9e1cab382458e843ac4356e3e00e1d35c36f449fa1a84488ab2c6557b392d"
    ),
    ILLUSIONER(
            "f108e349-dced-4aa1-a8b7-708b728780ad",
            "http://textures.minecraft.net/texture/512512e7d016a2343a7bff1a4cd15357ab851579f1389bd4e3a24cbeb88b"
    ),
    CREEPER(
            CompatibleSkullMaterial.CREEPER,
            "c66c91fd-6fb5-414f-b70e-39c19edf3d28",
            "http://textures.minecraft.net/texture/f4254838c33ea227ffca223dddaabfe0b0215f70da649e944477f44370ca6952"
    ),
    SKELETON(
            CompatibleSkullMaterial.SKELETON,
            "69708f16-9c00-4aa4-9089-247ec1c8d013",
            "http://textures.minecraft.net/texture/301268e9c492da1f0d88271cb492a4b302395f515a7bbf77f4a20b95fc02eb2"
    ),
    SPIDER(
            "3d461eb4-4601-4d76-b89f-e2bf0bcfc05c",
            "http://textures.minecraft.net/texture/cd541541daaff50896cd258bdbdd4cf80c3ba816735726078bfe393927e57f1"
    ),
    ZOMBIE(
            CompatibleSkullMaterial.ZOMBIE,
            "a1985e68-5743-42f5-b67a-8e8dd3f8eb11",
            "http://textures.minecraft.net/texture/56fc854bb84cf4b7697297973e02b79bc10698460b51a639c60e5e417734e11"
    ),
    SLIME(
            "625a4069-aa5d-4844-bea0-c133d6978373",
            "http://textures.minecraft.net/texture/895aeec6b842ada8669f846d65bc49762597824ab944f22f45bf3bbb941abe6c"
    ),
    GHAST(
            "7af3876e-0427-45c5-97ae-7119688cdecf",
            "http://textures.minecraft.net/texture/8b6a72138d69fbbd2fea3fa251cabd87152e4f1c97e5f986bf685571db3cc0"
    ),
    ZOMBIFIED_PIGLIN(
            "24d853db-b5b6-4b46-8cd7-4210c38d246d",
            "http://textures.minecraft.net/texture/e935842af769380f78e8b8a88d1ea6ca2807c1e5693c2cf797456620833e936f"
    ),
    ENDERMAN(
            "e229ba57-ec25-4501-87cb-af52d6ee7497",
            "http://textures.minecraft.net/texture/7a59bb0a7a32965b3d90d8eafa899d1835f424509eadd4e6b709ada50b9cf"
    ),
    CAVE_SPIDER(
            "c1c239ec-22c2-4d2e-ad15-8eb2d2770bcd",
            "http://textures.minecraft.net/texture/41645dfd77d09923107b3496e94eeb5c30329f97efc96ed76e226e98224"
    ),
    SILVERFISH(
            "49ff3529-bb63-44ee-99e6-bd57b888463d",
            "http://textures.minecraft.net/texture/da91dab8391af5fda54acd2c0b18fbd819b865e1a8f1d623813fa761e924540"
    ),
    BLAZE(
            "ca339549-96da-4f39-8cb8-ebd5c27b83a3",
            "http://textures.minecraft.net/texture/f30c14d1aadffe8d92f9bd8f938e220a15321ec5f39343d5f61e670aa7958631"
    ),
    MAGMA_CUBE(
            "58014cd9-af29-4ad0-ad62-2e9b16f749bb",
            "http://textures.minecraft.net/texture/38957d5023c937c4c41aa2412d43410bda23cf79a9f6ab36b76fef2d7c429"
    ),
    ENDER_DRAGON(
            CompatibleSkullMaterial.ENDER_DRAGON,
            "069773eb-ed19-4a82-8ea7-b38a7224e10b",
            "http://textures.minecraft.net/texture/74ecc040785e54663e855ef0486da72154d69bb4b7424b7381ccf95b095a"
    ),
    WITHER(
            "940486f2-1e1e-4d1f-b77f-1d47a1ba389f",
            "http://textures.minecraft.net/texture/3e4f49535a276aacc4dc84133bfe81be5f2a4799a4c04d9a4ddb72d819ec2b2b"
    ),
    BAT(
            "19e2f141-789d-4d5e-9cdd-8fbf54af2611",
            "http://textures.minecraft.net/texture/fb2d777922fee86588938e3193cf3f454ccf889fd31172b4f8c15be49f43388"
    ),
    WITCH(
            "9cc1d9c9-701e-4ecf-9d1b-0822b0a466eb",
            "http://textures.minecraft.net/texture/20e13d18474fc94ed55aeb7069566e4687d773dac16f4c3f8722fc95bf9f2dfa"
    ),
    ENDERMITE(
            "64ce8824-aa7d-456e-a46d-7a03b9fdf5fd",
            "http://textures.minecraft.net/texture/5bc7b9d36fb92b6bf292be73d32c6c5b0ecc25b44323a541fae1f1e67e393a3e"
    ),
    GUARDIAN(
            "5212ce39-d2a0-48d4-8c3d-b588fb970fcf",
            "http://textures.minecraft.net/texture/a0bf34a71e7715b6ba52d5dd1bae5cb85f773dc9b0d457b4bfc5f9dd3cc7c94"
    ),
    SHULKER(
            "eb8f9276-54f8-4ce0-917e-3479038e36d1",
            "http://textures.minecraft.net/texture/b1d3534d21fe8499262de87affbeac4d25ffde35c8bdca069e61e1787ff2f"
    ),
    PIG(
            "afb61daf-4fda-4ac4-9ddd-b2309377bcda",
            "http://textures.minecraft.net/texture/621668ef7cb79dd9c22ce3d1f3f4cb6e2559893b6df4a469514e667c16aa4"
    ),
    SHEEP(
            "f6aae326-8879-40dd-b491-1d6cb27d8e0d",
            "http://textures.minecraft.net/texture/f31f9ccc6b3e32ecf13b8a11ac29cd33d18c95fc73db8a66c5d657ccb8be70"
    ),
    COW(
            "1208f5e1-a040-4a4f-8462-372ffb1cd83d",
            "http://textures.minecraft.net/texture/5d6c6eda942f7f5f71c3161c7306f4aed307d82895f9d2b07ab4525718edc5"
    ),
    CHICKEN(
            "fadbdff9-a3e6-471a-8dc3-23e23f9c7acf",
            "http://textures.minecraft.net/texture/1638469a599ceef7207537603248a9ab11ff591fd378bea4735b346a7fae893"
    ),
    SQUID(
            "c6f5cd8b-1578-4f8b-a933-17ab193a6fe5",
            "http://textures.minecraft.net/texture/01433be242366af126da434b8735df1eb5b3cb2cede39145974e9c483607bac"
    ),
    WOLF(
            "e6f2820d-03e7-4718-b552-76e1aec11638",
            "http://textures.minecraft.net/texture/24d7727f52354d24a64bd6602a0ce71a7b484d05963da83b470360faa9ceab5f"
    ),
    MOOSHROOM(
            "b769f2da-e5a8-4882-b15b-d68618904a63",
            "http://textures.minecraft.net/texture/d0bc61b9757a7b83e03cd2507a2157913c2cf016e7c096a4d6cf1fe1b8db"
    ),
    SNOW_GOLEM(
            "82a04987-3371-41e2-ac43-91769b4461e7",
            "http://textures.minecraft.net/texture/1fdfd1f7538c040258be7a91446da89ed845cc5ef728eb5e690543378fcf4"
    ),
    OCELOT(
            "7f372b3d-c0fb-46df-ae19-4a9ee7584ae5",
            "http://textures.minecraft.net/texture/5657cd5c2989ff97570fec4ddcdc6926a68a3393250c1be1f0b114a1db1"
    ),
    IRON_GOLEM(
            "675bf087-86a1-48b3-b800-1f7d6042a585",
            "http://textures.minecraft.net/texture/89091d79ea0f59ef7ef94d7bba6e5f17f2f7d4572c44f90f76c4819a714"
    ),
    HORSE(
            "dc1293f0-c0cb-4a1e-973f-bd36d70a3de9",
            "http://textures.minecraft.net/texture/7bb4b288991efb8ca0743beccef31258b31d39f24951efb1c9c18a417ba48f9"
    ),
    RABBIT(
            "39a8931e-4c52-4db6-8acf-0a79746507c7",
            "http://textures.minecraft.net/texture/7d1169b2694a6aba826360992365bcda5a10c89a3aa2b48c438531dd8685c3a7"
    ),
    POLAR_BEAR(
            "da6aeada-b809-424b-a653-9ba6e4b01262",
            "http://textures.minecraft.net/texture/cd5d60a4d70ec136a658507ce82e3443cdaa3958d7fca3d9376517c7db4e695d"
    ),
    LLAMA(
            "dc8d5e11-535c-47b3-9565-74d94f1782c2",
            "http://textures.minecraft.net/texture/c2b1ecff77ffe3b503c30a548eb23a1a08fa26fd67cdff389855d74921368"
    ),
    PARROT(
            "2133c894-2dcc-4492-bbf5-29d4cad0d423",
            "http://textures.minecraft.net/texture/2b94f236c4a642eb2bcdc3589b9c3c4a0b5bd5df9cd5d68f37f8c83f8e3f1"
    ),
    VILLAGER(
            "08bf8f8a-61d5-4bc1-8761-4185f2fa3136",
            "http://textures.minecraft.net/texture/41b830eb4082acec836bc835e40a11282bb51193315f91184337e8d3555583"
    ),
    TURTLE(
            "3283acfe-e846-42a8-a555-7e2940b58ae4",
            "http://textures.minecraft.net/texture/0a4050e7aacc4539202658fdc339dd182d7e322f9fbcc4d5f99b5718a"
    ),
    PHANTOM(
            "385f1bf3-12d8-4246-87fc-2622a415a312",
            "http://textures.minecraft.net/texture/746830da5f83a3aaed838a99156ad781a789cfcf13e25beef7f54a86e4fa4"
    ),
    COD(
            "d8645871-ea91-4742-a0a3-0f86dc49653b",
            "http://textures.minecraft.net/texture/7892d7dd6aadf35f86da27fb63da4edda211df96d2829f691462a4fb1cab0"
    ),
    SALMON(
            "35968ced-177a-4cef-9b3f-a8cbcdc7659e",
            "http://textures.minecraft.net/texture/8aeb21a25e46806ce8537fbd6668281cf176ceafe95af90e94a5fd84924878"
    ),
    PUFFERFISH(
            "c5f9816d-7415-43f6-8e40-6f3b2dab3c33",
            "http://textures.minecraft.net/texture/17152876bc3a96dd2a2299245edb3beef647c8a56ac8853a687c3e7b5d8bb"
    ),
    TROPICAL_FISH(
            "6c2851ca-37b3-4f29-8415-c3d03f32e27d",
            "http://textures.minecraft.net/texture/40cd71fbbbbb66c7baf7881f415c64fa84f6504958a57ccdb8589252647ea"
    ),
    DROWNED(
            "937deebb-00ac-4304-adbf-d7968421a4a2",
            "http://textures.minecraft.net/texture/c3f7ccf61dbc3f9fe9a6333cde0c0e14399eb2eea71d34cf223b3ace22051"
    ),
    DOLPHIN(
            "9cd54916-35e6-4728-aac8-1850cb91d051",
            "http://textures.minecraft.net/texture/8e9688b950d880b55b7aa2cfcd76e5a0fa94aac6d16f78e833f7443ea29fed3"
    ),
    ARMOR_STAND(
            "912657c1-58f0-4a6c-b388-9666a0aaa750",
            "http://textures.minecraft.net/texture/26436f5791c0e659471a49a91f836c9fd309c28b083264bd92ba5e9bd3a768b8"
    ),
    GIANT(
            "a1c8704d-e52f-4f9d-8c7b-764cc9ded430",
            "http://textures.minecraft.net/texture/c09e16bccd9a4822fa75416ae38a7a98786e219dda679c5b127e3a2efcfc"
    );

    /**
     * The associated owning UUID of the enum entry - randomly assigned.
     */
    private final UUID owner;
    /**
     * The base64-encoded string representing the texture-url tags which define
     * the texture to display on a playerhead for the associated
     * entity/skulltype
     */
    private final String texture;
    /**
     * The material associated with the entity if one exists. Otherwise, this
     * will be CompatibleSkullMaterial.PLAYER
     *
     * @see org.bukkit.Material#PLAYER_HEAD
     */
    private final CompatibleSkullMaterial material;

    /**
     * an inner class containing mapping information / lookup information for
     * the skulltypes,materials, and UUIDs.
     */
    private static class Mappings {

        /**
         * Contains the UUID associated with the Player, must available here
         * prior to construction of enums.
         * <p>
         * Must match the UUID parameter of TexturedSkullType.PLAYER
         *
         * @see #PLAYER
         */
        public static final UUID playerUUID = UUID.fromString("a1ae4481-f3f0-4af9-a83e-75d3a7f87853");//must match above
        /**
         * A map of UUIDs to their associated skulltype for easier lookup.
         */
        public static final HashMap<UUID, TexturedSkullType> skullsById = new HashMap<>();
        /**
         * A map of Materials to their associated skulltype for easier lookup.
         * <p>
         * Note: only contains skulltypes with dedicated materials (vanilla
         * drops) and includes Playerhead materials mapping to PLAYER.
         */
        public static final HashMap<CompatibleSkullMaterial, TexturedSkullType> skullsByMaterial = new HashMap<>();
    }

    TexturedSkullType(CompatibleSkullMaterial material, String ownerUUID, String texture) {
        //this(material,wallMaterial,UUID.fromString(ownerUUID),texture);
        this.owner = UUID.fromString(ownerUUID);
        this.texture = texture;
        this.material = material;
        Mappings.skullsById.put(owner, this);
        if (hasDedicatedItem()) {
            Mappings.skullsByMaterial.put(material, this);
        }
    }

    TexturedSkullType(String ownerUUID, String texture) {
        this(CompatibleSkullMaterial.PLAYER, ownerUUID, texture);
    }

    /**
     * Get the UUID associated with the skulltype
     *
     * @return The UUID
     */
    @Override
    public UUID getOwner() {
        return owner;
    }

    /**
     * Get the Base64-encoded texture string associated with the skulltype
     *
     * @return A base64 string
     */
    @Override
    public URL getTexture() {
        try {
            return new URL(texture);
        }catch (Exception e){ return null; }
    }
    public String getTextureRaw() {
        return texture;
    }

    /**
     * Gets the CompatibleSkullMaterial enum indicating the material-sets for
     * the current server version.
     *
     * @return the compatible material
     */
    public CompatibleSkullMaterial getCompatibleMaterial() {
        return material;
    }

    /**
     * Find the skulltype associated with a provided UUID
     *
     * @param owner The UUID to find the skulltype for
     * @return if found: a TexturedSkullType, otherwise: null.
     */
    public static TexturedSkullType get(UUID owner) {
        return Mappings.skullsById.get(owner);
    }

    public static TexturedSkullType get(CompatibleSkullMaterial mat) {
        return Mappings.skullsByMaterial.get(mat);
    }

    /**
     * Finds the skulltype that has the provided Spawn-Name associated with it
     *
     * @param spawnname The spawn-name to find the skulltype for.
     * @return if found: a TexturedSkullType, otherwise: null.
     */
    public static TexturedSkullType getBySpawnName(String spawnname) {
        if (spawnname.isEmpty()) {
            return null;
        }
        for (TexturedSkullType type : TexturedSkullType.values()) {
            if (type.getSpawnName().equalsIgnoreCase(spawnname)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Get the droprate config name (key) for the given skulltype.
     *
     * @return A string containing the config entry name (key) for the skulltype
     */
    public String getConfigName() {
        if (this == TexturedSkullType.PLAYER) {
            return "droprate";
        }
        return name().replace("_", "").toLowerCase() + "droprate";
    }

    /**
     * Gets the item displayname for the associated skulltype, as defined in the
     * "lang" file.
     *
     * @return A string containing the skulltype's displayname
     */
    @Override
    public String getDisplayName() {
        String nameFormat = "HEAD_"+name();
        if(this==TexturedSkullType.PLAYER) nameFormat = "HEAD_PLAYER_NONE";
        return Formatter.format(Lang.getString(nameFormat));
    }

    /**
     * Gets the item displayname for a specific playerhead owner by username.
     * <p>
     * Note: This method is for resolving the displayname of PLAYER head drops,
     * it does not support legacy username-based mobhead displaynames.
     *
     * @param owner The username to get a displayname for
     * @return A string containing the user's head displayname
     */
    public static String getDisplayName(String owner) {
        String nameFormat = "HEAD_PLAYER";
        if(owner.toLowerCase().endsWith("s")) nameFormat = "HEAD_PLAYER_S";
        return Formatter.format(Lang.getString(nameFormat), owner);
    }

    /**
     * Get the "spawn" name for the associated skulltype, as defined in the
     * "lang" file.
     * <p>
     * This string is used to spawn-in the skull in external commands.
     *
     * @return A string containing the spawnname.
     */
    public String getSpawnName() {
        return Lang.getString("HEAD_SPAWN_" + name());
    }

    /**
     * Checks whether the skulltype uses a playerhead exclusively.
     * <p>
     * This indicates that either the skulltype is PLAYER or a mob without a
     * vanilla head item, generally.
     *
     * @return true: the skulls associated material was a playerhead. false: the
     * skull has a different associated material.
     */
    @Override
    public boolean isPlayerHead() {
        return this.material.getDetails().isBackedByPlayerhead();
    }

    /**
     * Checks whether the skulltype uses a specific material/item specific to
     * it.
     * <p>
     * This indicates wither the skulltype is PLAYER (PLAYERHEAD is dedicated to
     * it), or it has a vanilla head item associated with it, generally.
     *
     * @return true: the skulltype has a dedicated item/material or is of type
     * PLAYER. false: the skulltype is supported as a playerhead (the mob has no
     * special item/material associated).
     */
    public boolean hasDedicatedItem() {
        return (this.owner.equals(Mappings.playerUUID) || !isPlayerHead());
    }

    @Override
    public boolean isVanilla() {
        //Note: heads that never had items are set to Player materials
        // this.material.isSupported() gives us whether other types of vanilla head are 'supported' but this checks the same conditions as hasDedicatedItem:
        //    this==PLAYER || !getDetails().isBackedByPlayerhead();
        return hasDedicatedItem();
    }

    @Override
    public SkullDetails getImplementationDetails() {
        return this.material.getDetails();
    }
    
    /**
     * Implement API requirement that can't see this enum's type.
     * @return 
     */
    @Override
    public Enum toEnum() {
        return this;
    }
    
    /*
    @Override
    public boolean equals(HeadType head){
        if(head==null) return false;
        return this.owner.equals(head.getOwner());
    }
     */
    //NOTE: despite checkstyle warnings, you CANNOT implement hashCode in this enum!
}
